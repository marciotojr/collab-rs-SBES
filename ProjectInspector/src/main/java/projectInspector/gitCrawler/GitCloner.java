/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.gitCrawler;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author 
 */
public class GitCloner {

    public static void gitClone(String url, String path) throws GitAPIException {
        System.out.println("Cloning: "+url);
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException ex) {
            //pasta não existe
        }
        Git.cloneRepository()
                .setURI( url )
                .setDirectory( new File(path) )
                .call();
        System.out.println("Finished cloning "+url);
    }
    
 
}
