/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.ghtorrentAccess;

import collabrs_recommender.wrappers.nodejs.DAO.GHTorrentDAOInterface;
import collabrs_recommender.wrappers.nodejs.model.RepoModel;
import collabrs_recommender.wrappers.nodejs.model.UserModel;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author marcio
 */
public class GHTorrentProjectDAO extends GHTorrentDAOInterface<RepoModel, String> {

    @Override
    public RepoModel findById(int id) {
        RepoModel repo = null;
        try {
            open();
            statement = connect.createStatement();

            resultSet = statement
                    .executeQuery("SELECT * from projects WHERE id = " + id);

            if (resultSet.next()) {
                repo = new RepoModel();
                repo.setName(resultSet.getString("name"));
                repo.setId(resultSet.getInt("id"));
                repo.setDeleted(resultSet.getInt("deleted") != 0);
                repo.setDescription(resultSet.getString("description"));
                repo.setLanguage(resultSet.getString("language"));
                repo.setUrl(resultSet.getString("url"));
                repo.setOwner(new GHTorrentUserDAO().findById(resultSet.getInt("owner_id")));
            }
        } catch (Exception e) {

        }
        close();
        return repo;
    }

    @Override
    public RepoModel findByUnique(String unique) {
        RepoModel repo = null;
        String[] split = unique.split("/");
        String owner = split[split.length - 2];
        String repository = split[split.length - 1];
        try {
            open();
            preparedStatement = connect
                    .prepareStatement("SELECT p.* FROM projects p WHERE p.name like ? and p.owner_id = (SELECT u.id FROM users u WHERE u.login like ?)");

            preparedStatement.setString(1, repository);
            preparedStatement.setString(2, owner);

            resultSet = preparedStatement
                    .executeQuery();

            if (resultSet.next()) {
                repo = new RepoModel();
                repo.setId(resultSet.getInt("id"));
                repo.setName(resultSet.getString("name"));
                repo.setOwner(new GHTorrentUserDAO().findById(resultSet.getInt("owner_id")));
                repo.setDeleted(resultSet.getInt("deleted") != 0);
                repo.setDescription(resultSet.getString("description"));
                repo.setUrl(resultSet.getString("url"));
                repo.setLanguage(resultSet.getString("language"));
            }
        } catch (Exception e) {
            repo.setLanguage("");
        }
        close();
        return repo;
    }

    @Override
    public RepoModel insert(RepoModel object) {
        if (findByUnique(object.getUniqueValue()) == null) {
            try {
                open();
                GHTorrentUserDAO users = new GHTorrentUserDAO();
                users.insert(object.getOwner());
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("INSERT INTO projects VALUES (NULL, ?, ?, ?, ?, ? ,? , NULL, ?, ?)");
                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
                // Parameters start with 1
                preparedStatement.setString(1, object.getUrl());
                preparedStatement.setInt(2, object.getOwner().getId());
                preparedStatement.setString(3, object.getName());
                preparedStatement.setString(4, object.getDescription());
                preparedStatement.setString(5, object.getLanguage());
                preparedStatement.setTimestamp(6, new Timestamp(now.getTime()));
                preparedStatement.setInt(7, 0);
                preparedStatement.setTimestamp(8, new Timestamp(now.getTime()));

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        close();
        return findByUnique(object.getUniqueValue());
    }

    @Override
    public boolean update(RepoModel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RepoModel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<UserModel> listFollowers(RepoModel repo) {
        ArrayList<UserModel> followers = new ArrayList<UserModel>();
        open();
        this.insert(repo);
        repo = this.findByUnique(repo.getUrl());
        close();
        try {
            open();
            statement = connect.createStatement();

            resultSet = statement
                    .executeQuery("SELECT * from watchers WHERE repo_id = " + repo.getId());

            while (resultSet.next()) {
                GHTorrentUserDAO usersDAO = new GHTorrentUserDAO();
                followers.add(usersDAO.findById(resultSet.getInt("user_id")));
                
            }
        } catch (Exception e) {

        }
        close();
        
        
        return followers;
    }

    public void addFollower(RepoModel repo, UserModel user) {
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(user);
        addFollowers(repo, users);
    }

    public void addFollowers(RepoModel repo, List<UserModel> users) {
        open();
        this.insert(repo);
        repo = this.findByUnique(repo.getUrl());
        close();
        for (UserModel user : users) {
            try {
                open();
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("INSERT INTO watchers VALUES (?, ?, ?)");
                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
                // Parameters start with 1
                preparedStatement.setInt(1, repo.getId());
                preparedStatement.setInt(2, user.getId());
                preparedStatement.setTimestamp(3, new Timestamp(now.getTime()));

                preparedStatement.executeUpdate();
            }catch (MySQLIntegrityConstraintViolationException e){
                
            } catch (Exception e) {
                e.printStackTrace();
                close();
            }
            close();
        }
    }

}
