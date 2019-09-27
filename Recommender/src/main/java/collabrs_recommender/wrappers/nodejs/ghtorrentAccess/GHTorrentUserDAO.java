/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.ghtorrentAccess;

import collabrs_recommender.wrappers.nodejs.DAO.GHTorrentDAOInterface;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcio
 */
public class GHTorrentUserDAO extends GHTorrentDAOInterface<UserModel,String> {

    public GHTorrentUserDAO() {
        super();
    }

    @Override
    public UserModel findById(int id) {
        UserModel user = null;
        try {
            open();
            statement = connect.createStatement();

            resultSet = statement
                    .executeQuery("SELECT * from users WHERE id = " + id);

            if (resultSet.next()) {
                user = new UserModel(resultSet.getString("login"));
                user.setId(resultSet.getInt("id"));
                user.setDeleted(resultSet.getInt("deleted")!=0);
                user.setCountry_code(resultSet.getString("country_code"));
                user.setType(resultSet.getString("type"));
                user.setState(resultSet.getString("state"));
                user.setCity(resultSet.getString("city"));
                user.setLocation(resultSet.getString("location"));
            }
        } catch (Exception e) {

        }
        close();
        return user;
    }

    @Override
    public UserModel findByUnique(String login) {
        UserModel user = null;
        try {
            open();
            statement = connect.createStatement();

            resultSet = statement
                    .executeQuery("SELECT * from users WHERE login LIKE '" + login + "'");
            if (resultSet.next()) {
                user = new UserModel(resultSet.getString("login"));
                user.setId(resultSet.getInt("id"));
                user.setDeleted(resultSet.getInt("deleted")!=0);
                user.setCountry_code(resultSet.getString("country_code"));
                user.setType(resultSet.getString("type"));
                user.setState(resultSet.getString("state"));
                user.setCity(resultSet.getString("city"));
                user.setLocation(resultSet.getString("location"));
            }
        } catch (Exception e) {

        }
        close();
        return user;
    }

    @Override
    public UserModel insert(UserModel object) {
        if (findByUnique(object.getLogin()) == null) {
            try {
                open();
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ? ,? ,?)");
                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
                // Parameters start with 1
                preparedStatement.setString(1, object.getLogin());
                preparedStatement.setString(2, "USR");
                preparedStatement.setInt(3, 0);
                preparedStatement.setString(4, object.getCountry_code());
                preparedStatement.setString(5, object.getState());
                preparedStatement.setString(6, object.getCity());
                preparedStatement.setString(7, object.getLocation());
                
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
               close();
            }
            close();
        }
        close();
        return findByUnique(object.getLogin());
    }

    @Override
    public boolean update(UserModel object) {
        if (findByUnique(object.getLogin()) != null) {
            try {
                open();
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("UPDATE users SET login=(?),type=(?),deleted=(?),country_code=(?),state=(?),city=(?),location=(?) WHERE login like (?)");
                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
                // Parameters start with 1
                preparedStatement.setString(1, object.getLogin());
                preparedStatement.setString(2, "USR");
                preparedStatement.setInt(3, 0);
                preparedStatement.setString(4, object.getCountry_code());
                preparedStatement.setString(5, object.getState());
                preparedStatement.setString(6, object.getCity());
                preparedStatement.setString(7, object.getLocation());
                preparedStatement.setString(8, object.getLogin());
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                close();
                return false;
            }
            close();
            return true;
        }
        close();
        return false;
    }

    @Override
    public boolean delete(UserModel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<RepoModel> listOwned(UserModel owner) {
        ArrayList<RepoModel> owned = new ArrayList<RepoModel>();
        open();
        this.insert(owner);
        owner = this.findByUnique(owner.getLogin());
        close();
        try {
            open();
            statement = connect.createStatement();

            resultSet = statement
                    .executeQuery("SELECT * from projects WHERE owner_id = " + owner.getId());

            while (resultSet.next()) {
                GHTorrentProjectDAO repos = new GHTorrentProjectDAO();
                owned.add(repos.findById(resultSet.getInt("id")));
                
            }
        } catch (Exception e) {

        }
        close();
        
        
        return owned;
    }
}
