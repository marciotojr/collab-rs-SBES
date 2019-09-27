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
public class RepoModel extends ObjectModel {

    int id;
    String url;
    UserModel owner;
    String name;
    String description;
    String language;
    RepoModel forked_from;
    boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.url = s;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        if(s==null)return;
        s = s.substring(0, Math.min(s.length(), 255));
        this.name = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.description = s;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String s) {
        s = s.substring(0, Math.min(s.length(), 255));
        this.language = s;
    }

    public RepoModel getForked_from() {
        return forked_from;
    }

    public void setForked_from(RepoModel forked_from) {
        this.forked_from = forked_from;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getUniqueValue() {
        return getUniqueValue(this.getUrl());
    }

    public static String getUniqueValue(String unique) {
        try {
            String[] split = unique.split("/");
            return split[split.length - 2] + "/" + split[split.length - 1];
        } catch (Exception e) {
            return "";
        }
    }
}
