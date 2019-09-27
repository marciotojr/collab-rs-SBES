/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author marcio
 */
public abstract class GHTorrentDAOInterface<T,S> extends BasicDAOInterface<T,S>{
    
    protected Connection connect = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    
    
    
    protected GHTorrentDAOInterface(){
        super();
    }

    protected void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connect = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/ghtorrent", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't connect to the database");
        }
    }

    // You need to close the resultSet
    protected void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println("Couldn't close connection");
        }
    }
    
}
