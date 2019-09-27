package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentProjectDAO;
import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentUserDAO;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.NoRemainingRequestsException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import collabrs_recommender.utils.CommandThreadManager;
import org.json.JSONObject;

/**
 *
 * @author marci
 */
public class RepositoryService extends CommandService {

    public RepositoryService() {
        super();
    }

    private JSONObject sendGet(String object) throws Exception {
        String url = "https://api.github.com/repos/" + object;

        return new JSONObject(getContent(url));

    }

    @Override
    public void increment() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void getData(String object) {
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        RepoModel repo = repoDAO.findByUnique(object);
        try {
            if (repo == null) {
                JSONObject repoJSON = sendGet(object);
                UserService us = new UserService();
                us.getData(repoJSON.getJSONObject("owner").getString("login"));
                repo = generateModel(repoJSON);
            }
            UserModel owner = repo.getOwner();

            int id = repo.getId();
            String project = owner.getLogin() + "/" + repo.getName();
            String projectSuffix = "github_project_" + project;
            String username = owner.getLogin();
            String ownerSuffix = "github_user_" + username;
            //String name = userJSON.getString("name");
            op.createIndividual(projectSuffix, "Software");
            op.createIndividual(ownerSuffix, "Individual");

            op.setIntegerProperty(projectSuffix, id, "hasId");
            op.setObjectProperty(ownerSuffix, projectSuffix, "owns");
            op.setObjectProperty(ownerSuffix, projectSuffix, "develops");
            op.setStringProperty(projectSuffix, project, "hasName");
            op.setStringProperty(projectSuffix, repo.getUrl(), "hasURL");
//            if (repo.getLanguage().toLowerCase().equals("javascript") || repo.getLanguage().toLowerCase().equals("typescript")) {
//                OntolibApplication.repoWalkers.add(new DependencyService(project));
//                queueItens(username, project);
//            }

        } catch (PageForbiddenException e) {
            e.printStackTrace();
        } catch (NoRemainingRequestsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queueItens(String owner, String project) {
        CommandThreadManager.getInstance().getCommandService("user").getQueue().put(owner);
//        CommandThreadManager.getInstance().getCommandService("owned").getQueue().put(owner);
//        CommandThreadManager.getInstance().getCommandService("contributors").getQueue().put(project);
//        CommandThreadManager.getInstance().getCommandService("followers").getQueue().put(project);
//        CommandThreadManager.getInstance().getCommandService("languages").getQueue().put(project);
    }

    public static RepoModel generateModel(JSONObject repoJSON) {
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        RepoModel repo = new RepoModel();

        GHTorrentUserDAO userDAO = new GHTorrentUserDAO();

        UserModel owner = userDAO.findByUnique(repoJSON.getJSONObject("owner").getString("login"));
        repo.setOwner(owner);
        try {
            repo.setDescription(repoJSON.getString("description"));
        } catch (Exception e) {
        }
        try {
            String[] full_name = repoJSON.getString("full_name").split("/");
            String name = full_name[full_name.length - 1];
            repo.setName(name);
        } catch (Exception e) {
        }
        try {
            repo.setName(repoJSON.getString("name"));
        } catch (Exception e) {
        }
        try {
            repo.setLanguage(repoJSON.getString("language"));
        } catch (Exception e) {
        }
        try {
            repo.setUrl(repoJSON.getString("url"));
        } catch (Exception e) {
        }
        return repoDAO.insert(repo);
    }

}
