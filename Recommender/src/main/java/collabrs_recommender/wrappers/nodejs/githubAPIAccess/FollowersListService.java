/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.ontologyQuerying.OntologyPopulator;
import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentProjectDAO;
import collabrs_recommender.wrappers.nodejs.model.ObjectModel;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import java.util.List;
import collabrs_recommender.utils.CommandThreadManager;

/**
 *
 * @author
 */
public class FollowersListService extends UserListService {

    public static OntologyPopulator op = OntologyPopulator.getInstance();

    public FollowersListService() {
        super();
    }

    @Override
    protected String getURLPrefix() {
        return "https://api.github.com/repos/";
    }

    @Override
    protected String getURLSuffix() {
        return "/stargazers";
    }

    @Override
    protected void relateIndividuals(UserModel user, ObjectModel object) {
        RepoModel repo = (RepoModel) object;
        String projectSuffix = "github_project_" + repo.getUniqueValue();
        String userSuffix = "github_user_" + user.getLogin();
        op.createIndividual(projectSuffix, "Software");
        op.createIndividual(userSuffix, "Entity");
        op.setObjectProperty(userSuffix, projectSuffix, "follows");
        CommandThreadManager.getInstance().getCommandService("owned").getQueue().put(user.getLogin());

    }

    @Override
    protected ObjectModel getModel(String object) {
        RepositoryService rs = new RepositoryService();
        rs.getData(object);
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        return repoDAO.findByUnique(object);
    }

    @Override
    public boolean populateFromDataBase(String object) {
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        RepoModel repo = repoDAO.findByUnique(object);
        List<UserModel> followers = repoDAO.listFollowers(repo);
        if (followers.isEmpty()) {
            return false;
        }
        for (UserModel user : followers) {
            relateIndividuals(user, repo);
        }
        return true;
    }

    @Override
    protected void saveToDatabase(UserModel user, ObjectModel object) {
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        RepoModel repo = (RepoModel) object;
        repoDAO.addFollower(repo, user);
    }

}
