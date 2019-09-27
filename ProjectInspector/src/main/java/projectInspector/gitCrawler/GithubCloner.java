/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.gitCrawler;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author 
 */
public class GithubCloner extends GitCloner {

    public void gitClone(String repo) throws GitAPIException {
        this.gitClone("https://github.com/" + repo + ".git", "./" + repo);
    }

    public static void main(String[] argsv) throws GitAPIException {
        GithubCloner ghc = new GithubCloner();
        ghc.gitClone("expressjs/express");
    }
    
    
}
