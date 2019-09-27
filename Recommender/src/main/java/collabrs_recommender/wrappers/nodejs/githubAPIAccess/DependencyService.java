/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess;
 

import collabrs_recommender.ontologyQuerying.OntologyPopulator;
import collabrs_recommender.utils.ReadFile;
import collabrs_recommender.utils.ThreadManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import projectInspector.ProjectInspector;
import projectInspector.gitCrawler.Walker;

/**
 *
 * @author
 */
public class DependencyService implements Runnable {

    public static OntologyPopulator op = OntologyPopulator.getInstance();
    ProjectInspector pi;
    String repo;


//    public DependencyService(String repo) {
//        this.repo = repo;
//        pi = new ProjectInspector(repo);
//        String[] split = repo.split("/");
//        if (!split[split.length - 1].equals("null")) {
//            OntolibApplication.repoWalkers.add(new Thread(this));
//        }
//    }
    public DependencyService(String repo) {
        this.repo = repo;
        pi = new ProjectInspector(repo);
    }

    public void run() {
        String path = "temp" + File.separatorChar + repo.replace('/', File.separatorChar) + File.separatorChar + "dependencies.collabrs";
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            JSONObject object = new JSONObject(ReadFile.read(path));
            Map<String, Object> map = object.getJSONObject("dependencies").toMap();
            for (String email : map.keySet()) {
                try {
                    List<String> dependencies = (List) map.get(email);
                    for (String dependency : dependencies) {
                        dependency = dependency.replace("'", "").replace("\"", "");
                        String projectSuffix = "github_project_" + this.repo;
                        op.createIndividual(projectSuffix, "Software");
                        String dep = "dep_" + dependency;
                        dep = dep.replace("#", "");
                        op.createIndividual(dep, "Product");
                        op.setStringProperty(dep, dependency, "hasName");
                        op.setObjectProperty(projectSuffix, dep, "requiresKnowledge");
                        if (!email.equals("")) {
                            email = email.replace("\"", "");
                            op.createIndividual(email, "Individual");
                            op.setObjectProperty(email, dep, "hasUsed");
                            op.setStringProperty(email, email, "hasEmail");
                            op.setObjectProperty(email, projectSuffix, "develops");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } else {
            pi.run();
            Walker walker = pi.getWalker();
            try {
                BufferedWriter writer;
                writer = new BufferedWriter(new FileWriter("temp" + File.separatorChar + repo.replace('/', File.separatorChar) + File.separatorChar + "dependencies.collabrs"));
                System.out.println("Creating: " + repo + "/dependencies.collabrs");
                JSONObject object = new JSONObject();
                object.put("dependencies", walker.getKnowledge());
                for (String email : walker.getKnowledge().keySet()) {
                    try {
                        HashSet<String> dependencies = walker.getKnowledge().get(email);
                        for (String dependency : dependencies) {
                            dependency = dependency.replace("'", "").replace("\"", "");
                            String projectSuffix = "github_project_" + this.repo;
                            op.createIndividual(projectSuffix, "Software");
                            String dep = "dep_" + dependency;
                            dep = dep.replace("#", "");
                            op.createIndividual(dep, "Topic");
                            op.setStringProperty(dep, dependency, "hasName");
                            op.setObjectProperty(projectSuffix, dep, "requiresKnowledge");
                            if (!email.equals("")) {
                                email = email.replace("\"", "");
                                op.createIndividual(email, "Individual");
                                op.setObjectProperty(email, dep, "isAquaintedWith");
                                op.setStringProperty(email, email, "hasEmail");
                                op.setObjectProperty(email, projectSuffix, "develops");
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                writer.write(object.toString());
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    private static void generateFiles(String path) {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                processUser(f);
            } else {

            }
        }
    }

    private static void processUser(File folder) {
        File[] repos = folder.listFiles();

        if (repos == null) {
            return;
        }

        for (File f : repos) {
            if (f.isDirectory()) {
                processRepo(f);
            } else {

            }
        }
    }

    private static void processRepo(File f) {
        DependencyService ds = new DependencyService(f.getPath());
        tm.add(ds);
    }
    static ThreadManager tm;

    public static void main(String[] args) {
        tm = new ThreadManager("dependencies");
        new Thread(tm).start();
        generateFiles("/home/marcio/Projects/ontoservice/temp");
    }
}
