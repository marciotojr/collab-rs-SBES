/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.recommendation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import collabrs_recommender.ontologyQuerying.OntologyQuery;
import collabrs_recommender.utils.ReadFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author marcio
 */
public class Recommender {

    Map<String, HashSet<String>> contributionMap;
    Map<String, HashSet<String>> dependencyMap;
    Map<String, HashSet<String>> acquaintanceMap;
    Map<String, HashSet<String>> knowledgeMap;
    Map<String, Float> rank;
    Set<String> dependencies;
    Set<String> allDependencies;
    final String target_project;

    public Recommender(String target_project) {
        this.dependencyMap = new HashMap<String, HashSet<String>>();
        this.contributionMap = new HashMap<String, HashSet<String>>();
        this.acquaintanceMap = new HashMap<String, HashSet<String>>();
        this.knowledgeMap = new HashMap<String, HashSet<String>>();
        this.rank = new HashMap<String, Float>();
        this.dependencies = new HashSet<String>();
        this.allDependencies = new HashSet<String>();
        this.target_project = target_project;
        init();
    }

    private void init() {
        String content = ReadFile.read("/path/to/target/package.json");
        JSONObject json = new JSONObject(content);
        for (String dep : json.getJSONObject("dependencies").keySet()) {
            dependencies.add("dep_" + dep);
            allDependencies.add("dep_" + dep);
        }
        for (String dep : json.getJSONObject("devDependencies").keySet()) {
            allDependencies.add("dep_" + dep);
        }
        String query;
        JSONArray output;
        query = OntologyQuery.getInstance().executeQuery("SELECT ?object WHERE { secon:github_project_" + this.target_project + " secon:dependsOn ?object }");
        output = new JSONObject(query).getJSONArray("output");
        for (int i = 0; i < output.length(); i++) {
            JSONObject line = (JSONObject) output.get(i);
            allDependencies.add(line.getString("object"));
        }
        query = OntologyQuery.getInstance().executeQuery("SELECT ?subject ?object WHERE { ?subject secon:isAquaintedWith ?object }");
        output = new JSONObject(query).getJSONArray("output");
        for (int i = 0; i < output.length(); i++) {
            JSONObject line = (JSONObject) output.get(i);
            acquaintanceMap.get(line.getString("subject")).add(line.getString("object"));
        }
        query = OntologyQuery.getInstance().executeQuery("SELECT ?subject ?object WHERE { ?subject secon:knows ?object }");
        output = new JSONObject(query).getJSONArray("output");
        for (int i = 0; i < output.length(); i++) {
            JSONObject line = (JSONObject) output.get(i);
            knowledgeMap.get(line.getString("subject")).add(line.getString("object"));
        }
        query = OntologyQuery.getInstance().executeQuery("SELECT ?subject ?object WHERE { ?subject secon:dependsOn ?object. secon:github_project_" + this.target_project + "  secon:dependsOn ?object}");
        output = new JSONObject(query).getJSONArray("output");
        for (int i = 0; i < output.length(); i++) {
            JSONObject line = (JSONObject) output.get(i);
            dependencyMap.get(line.getString("subject")).add(line.getString("object"));
        }
        query = OntologyQuery.getInstance().executeQuery("SELECT ?subject ?object WHERE { ?subject secon:develops ?object }");
        output = new JSONObject(query).getJSONArray("output");
        for (int i = 0; i < output.length(); i++) {
            JSONObject line = (JSONObject) output.get(i);
            contributionMap.get(line.getString("subject")).add(line.getString("object"));
        }
    }

    private HashMap<String, Float> rankKnown() {
        HashMap<String, Float> rank = new HashMap<String, Float>();
        for (String developer : knowledgeMap.keySet()) {
            float count = 0;
            for (String knownDep : knowledgeMap.get(developer)) {
                for (String dep : this.dependencies) {
                    if (knownDep.equals(dep)) {
                        count++;
                    }
                }
            }
            float value = count / (float) this.dependencies.size();
            rank.put(developer, value);
            System.out.println(developer + ": " + value);
        }
        return rank;
    }

    private HashMap<String, Float> rankAcquaintance() {
        HashMap<String, Float> rank = new HashMap<String, Float>();
        for (String developer : acquaintanceMap.keySet()) {
            float count = 0;
            for (String knownDep : acquaintanceMap.get(developer)) {
                for (String dep : this.allDependencies) {
                    if (knownDep.equals(dep)) {
                        count++;
                    }
                }
            }
            float value = count / (float) this.allDependencies.size();
            rank.put(developer, value);
            System.out.println(developer + ": " + value);
        }
        return rank;
    }

    private HashMap<String, Float> rankSimilarity() {
        HashMap<String, Float> rank = new HashMap<String, Float>();

        for (String developer : knowledgeMap.keySet()) {
            rank.put(developer, (float) 0);
            for (String project : contributionMap.get(developer)) {
                if (dependencyMap.get(project).size() > rank.get(developer)) {
                    rank.put(developer, (float)dependencyMap.get(project).size());
                }
            }
            rank.put(developer, rank.get(developer)/(float)this.allDependencies.size());
        }

        return rank;
    }

    public void recommend() {
        HashMap<String, Float> knowledge = rankKnown();
        HashMap<String, Float> acquaintance = rankAcquaintance();
        HashMap<String, Float> similarity = rankSimilarity();
        for(String developer:knowledge.keySet()){
            float rank = (knowledge.get(developer)+acquaintance.get(developer))/2*similarity.get(developer);
            System.out.println(developer+", "+knowledge.get(developer)+", "+acquaintance.get(developer)+", "+similarity.get(developer)+", "+rank);
        }
    }

}
