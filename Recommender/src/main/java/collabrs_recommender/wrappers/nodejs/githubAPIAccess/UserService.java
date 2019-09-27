package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

import collabrs_recommender.wrappers.nodejs.ghtorrentAccess.GHTorrentUserDAO;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import org.json.JSONObject;

/**
 *
 * @author marci
 */
public class UserService extends CommandService {

    public UserService() {
        super();
    }

    private JSONObject sendGet(String object) throws Exception, PageForbiddenException {
        String url = "https://api.github.com/users/" + object;

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
        GHTorrentUserDAO userDAO = new GHTorrentUserDAO();
        try {
            UserModel user = userDAO.findByUnique(object);
            if (user == null) {
                JSONObject userJSON = sendGet(object);
                user = generateModel(userJSON);
            }
            String userSuffix = "github_user_" + user.getLogin();

            op.createIndividual(userSuffix, "Entity");

            op.setStringProperty(userSuffix, user.getLogin(), "hasLogin");
            op.setStringProperty(userSuffix, "https://api.github.com/users/" + user.getLogin(), "hasURL");
            op.setStringProperty(userSuffix, user.getLocation(), "hasLocation");
//            CommandThreadManager.getInstance().getCommandService("owned").getQueue().put(user.getLogin());
//            CommandThreadManager.getInstance().getCommandService("leaf").getQueue().put(user.getLogin());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static UserModel generateModel(JSONObject userJSON){
                UserModel user = new UserModel(userJSON.getString("login"));
                try {
                    user.setLocation(userJSON.getString("location"));
                } catch (Exception e) {
                }
                GHTorrentUserDAO userDAO = new GHTorrentUserDAO();
                userDAO.insert(user);
                return userDAO.findByUnique(userJSON.getString("login"));
    }

}
