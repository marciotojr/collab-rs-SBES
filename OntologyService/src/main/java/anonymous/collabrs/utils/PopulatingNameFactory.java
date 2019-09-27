/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.utils;

/**
 *
 */
public class PopulatingNameFactory {
    static int i=0; 
    public static String generateName(String name){
        i++;
        return name.replace(".owl", "-populated-"+i+".owl");
    }
    
    public static String getGeneratedName(String name){
        return name.replace(".owl", "-populated-"+i+".owl");
    }
}
