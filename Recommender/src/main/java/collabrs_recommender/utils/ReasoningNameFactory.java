/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.utils;

/**
 *
 */
public class ReasoningNameFactory {
    public static String generateName(String name){
        return name.replace(".owl", "-infered.owl");
    }
}
