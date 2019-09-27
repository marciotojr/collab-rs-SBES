/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.DAO;

import java.util.List;

/**
 *
 * @author marcio
 */
public abstract class BasicDAOInterface<T,S> {
    
    protected BasicDAOInterface(){
        
    }

    public abstract T findById(int id);
    public abstract T findByUnique(S unique);
    public abstract T insert(T object);
    public abstract boolean update(T object);
    public abstract boolean delete(T object);
}
