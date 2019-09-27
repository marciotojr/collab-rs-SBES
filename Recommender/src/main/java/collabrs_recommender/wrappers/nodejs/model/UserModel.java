/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.model;

/**
 *
 * @author marcio
 */
public class UserModel extends ObjectModel{
    int id;
    String login;
    String type;
    boolean deleted;
    String country_code;
    String state;
    String city;
    String location;
    String email;

    public UserModel(String login) {
        this.setLogin(login);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.login = s;
    }

    public String getType() {
        return type;
    }

    public void setType(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.type = s;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String s) {
        s = s.substring(0, Math.min(s.length(), 3));
        this.country_code = s;
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.state = s;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.city = s;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.location = s;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.email = s;
    }

    @Override
    public String getUniqueValue() {
        return this.getLogin();
    }
    
}
