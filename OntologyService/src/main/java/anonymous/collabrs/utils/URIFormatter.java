/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.utils;

/**
 *
 */
public abstract class URIFormatter {
    public static String format(String uri){
        return uri.replace("[", "-_").replace("]", "_-").replace("#", "_-_");
    }
}
