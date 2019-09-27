/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentProjectDAO;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.NoRemainingRequestsException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author
 */
public abstract class RepositoryListService extends CommandService {

    int page;

    public RepositoryListService() {
        super();
    }

    protected abstract String getURLPrefix();

    protected abstract String getURLSuffix();

    private JSONArray sendGet(String object) throws Exception {
        String url = getURLPrefix() + object + getURLSuffix() + "?per_page=100&page=" + page;

        return new JSONArray(getContent(url));

    }

    @Override
    public void increment() {
    }

    @Override
    public void initialize() {
    }

    public abstract boolean populateFromDataBase(String object);

    @Override
    public void getData(String object) throws PageForbiddenException, NoRemainingRequestsException {
        if (populateFromDataBase(object)) {
            return;
        }
        try {
            GHTorrentProjectDAO repoDAO = new GHTorrentProjectDAO();
            page = 0;
            while (true) {
                page++;
                JSONArray jArray = sendGet(object);
                if (jArray.length() == 0) {
                    break;
                }
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject projectJSON = (JSONObject) jArray.getJSONObject(i);

                    RepoModel repo = RepositoryService.generateModel(projectJSON);

                    relateIndividuals(repo, object);

                }
            }
            System.out.println(object + " analisys ended");
        } catch (JSONException e) {
        } catch (PageForbiddenException e) {
            throw e;
        } catch (NoRemainingRequestsException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void relateIndividuals(RepoModel repo, String object);

    protected abstract void saveToDatabase(JSONObject user, String object);

}
