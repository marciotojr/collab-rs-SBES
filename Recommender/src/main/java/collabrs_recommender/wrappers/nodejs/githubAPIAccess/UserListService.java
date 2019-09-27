/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentUserDAO;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.NoRemainingRequestsException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;
import collabrs_recommender.wrappers.nodejs.model.ObjectModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author
 */
public abstract class UserListService extends CommandService {
    
    int page;
    
    public UserListService() {
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
        JSONArray jArray = null;
        try {
            GHTorrentUserDAO userDAO = new GHTorrentUserDAO();
            ObjectModel objModel = getModel(object);
            page = 0;
            while (true) {
                page++;
                jArray = sendGet(object);
                if (jArray.length() == 0) {
                    break;
                }
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject userJSON = (JSONObject) jArray.getJSONObject(i);
                    String currentUser = userJSON.getString("login");
                    UserModel user = userDAO.findByUnique(currentUser);
                    if (user == null) {
                        user = UserService.generateModel(userJSON);
                        user = userDAO.insert(user);
                    }
                    String userSuffix = "github_user_" + user.getLogin();
                    
                    op.createIndividual(userSuffix, "Individual");
                    op.setIntegerProperty(userSuffix, user.getId(), "hasId");
                    op.setStringProperty(userSuffix, user.getLogin(), "hasLogin");
                    op.setStringProperty(userSuffix, "https://api.github.com/users/" + user.getLogin(), "hasURL");
                    op.setStringProperty(userSuffix, user.getLocation(), "hasLocation");
//                    CommandThreadManager.getInstance().getCommandService("owned").getQueue().put(user.getLogin());
                    saveToDatabase(user, objModel);
                    relateIndividuals(user, objModel);
                    
                }
            }
        } catch (PageForbiddenException e) {
            throw e;
        } catch (NoRemainingRequestsException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected abstract void relateIndividuals(UserModel user, ObjectModel object);

    protected abstract void saveToDatabase(UserModel user, ObjectModel object);
    
    protected abstract ObjectModel getModel(String object);
    
}
