/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.ontologyQuerying.OntologyPopulator;
import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentProjectDAO;
import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentUserDAO;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import java.util.List;
import collabrs_recommender.OntolibApplication;
import org.json.JSONObject;

/**
 *
 * @author
 */
public class OwnedRepoService extends RepositoryListService {
    
    public static OntologyPopulator op = OntologyPopulator.getInstance();

    public OwnedRepoService() {
        super();
    }

    @Override
    protected String getURLPrefix() {
        return "https://api.github.com/users/";
    }

    @Override
    protected String getURLSuffix() {
        return "/repos";
    }

    @Override
    protected void relateIndividuals(RepoModel repo, String object) {
        if (repo == null) {
            GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
            repo = repoDAO.findByUnique(object);
        }
        try {
            if (repo.getLanguage().toLowerCase().equals("javascript") || repo.getLanguage().toLowerCase().equals("typescript")) {
                String userSuffix = "github_user_" + repo.getOwner().getUniqueValue();
                String repoSuffix = "github_project_" + repo.getUniqueValue();
                op.createIndividual(userSuffix, "Individual");
                op.createIndividual(repoSuffix, "Software");
                op.setObjectProperty(userSuffix, repoSuffix, "develops");
                op.setObjectProperty(userSuffix, repoSuffix, "owns");
                OntolibApplication.repoWalkers.add(new DependencyService(repo.getUniqueValue()));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean populateFromDataBase(String object) {
        GHTorrentUserDAO userDAO = new GHTorrentUserDAO();
        UserModel user = userDAO.findByUnique(object);
        List<RepoModel> owned = userDAO.listOwned(user);
        if (owned.isEmpty()) {
            return false;
        }
        for (RepoModel repo : owned) {
            relateIndividuals(repo, repo.getUniqueValue());
        }
        return true;
    }

    @Override
    protected void saveToDatabase(JSONObject user, String object) {
        GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
        RepoModel repo = repoDAO.findByUnique(object);
        if (repo == null) {
            repoDAO.insert(repo);
        }
    }

}
