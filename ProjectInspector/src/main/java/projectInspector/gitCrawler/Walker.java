/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.gitCrawler;

import projectInspector.blame.Blamer;
import projectInspector.grammars.antlr.dependencyChecker.Usage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class Walker {

    private String path;
    private HashMap<String, HashSet> knowledge;

    public void walk(String recursivePath) {

        File root = new File(recursivePath);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                if (!(f.getName().equals(".git"))) {
                    walk(f.getAbsolutePath());
                }
            } else {
                if (f.getName().endsWith(".js")) {
                    String pathRel = f.getAbsoluteFile().getPath();
                    pathRel = pathRel.substring(pathRel.indexOf(path) + path.length() + 1);
                    try {
                        HashMap<Usage, String> map = Blamer.search(path, pathRel);
                        for (Usage usage : map.keySet()) {
                            String user = map.get(usage);
                            String dependency = usage.getImportStatement().getDependency();
                            if (knowledge.get(user) == null) {
                                knowledge.put(user, new HashSet<String>());
                            }
                            knowledge.get(user).add(dependency);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Walker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (f.getName().toLowerCase().equals("package.json")) {
                    DevDependencies dd = new DevDependencies(f);
                    ArrayList<String> dependencies = dd.getDependencies();
                    for (String dependency : dependencies) {
                        if (knowledge.get("") == null) {
                            knowledge.put("", new HashSet<String>());
                        }
                        knowledge.get("").add(dependency);
                    }
                }
            }
        }
    }

    public Walker(String path) {
        this.path = path;
        knowledge = new HashMap<String, HashSet>();
    }

    public void walk() {
        this.walk(this.path);
    }

    public HashMap<String, HashSet> getKnowledge() {
        return knowledge;
    }
}
