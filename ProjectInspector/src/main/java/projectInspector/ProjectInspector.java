/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector;

import projectInspector.gitCrawler.GitCloner;
import projectInspector.gitCrawler.Walker;
import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

/**
 *
 * @author 
 */
public class ProjectInspector implements Runnable {

    private String repo;
    private Walker fw;

    public ProjectInspector(String repo) {
        System.out.println("Inspecting project: " + repo);
        repo = repo.replace(".git", "");
        String[] arr = repo.split("/");
        repo = arr[arr.length - 2] + "/" + arr[arr.length - 1];
        this.repo = repo;
        String spath = "temp" + File.separatorChar + repo;
        File path = new File(spath);

    }

    @Override
    public void run() {
        try {
            String path = "temp" + File.separatorChar + repo;
            if (!new File(path).exists()) {
                GitCloner.gitClone("https://github.com/" + repo + ".git", path);
            }
            fw = new Walker(path);
            Repository repo = new FileRepository(path);
            fw.walk();

            for (String email : fw.getKnowledge().keySet()) {
                HashSet<String> dependencies = fw.getKnowledge().get(email);
                for (String dependency : dependencies) {
                    System.out.println(email + " -> " + dependency);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProjectInspector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new ProjectInspector("webpack/webpack");

    }

    public Walker getWalker() {
        return fw;
    }

}
