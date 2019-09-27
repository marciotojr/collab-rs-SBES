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
public abstract class ObjectModel {
    public abstract String getUniqueValue();  
    
    @Override
    public String toString(){
        return getUniqueValue();
    }
}
