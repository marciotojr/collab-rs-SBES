/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.ontologyAccess.ontologyQuerying;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 */
public class OntologyQuery {

    public static String executeQuery(String query, Model model) {
        if (query == null || query.equals("")) {
            query = "SELECT DISTINCT ?subject ?property ?object \n"
                    + "WHERE{{ ?subject ?property ?object }}";
        }
        query = query.replace("{", " { ").replace("}", " } ");
        String[] split = query.split(" ");
        Map<String,String> mapPref = OntologyPopulator.getPrefixMap();
        for (int i = 0; i < split.length; i++) {
            String string = split[i];
            for (String key : OntologyPopulator.getPrefixMap().keySet()) {
                if (string.contains(key + ":")) {
                    string = string.replace(key + ":", OntologyPopulator.getPrefixMap().get(key));
                    split[i] = "<" + string + ">";
                }
            }
        }
        query = "";
        for (int i = 0; i < split.length; i++) {
            query += split[i]+" ";
            
        }
        ArrayList<HashMap> list = new ArrayList<HashMap>();

        Dataset dataset = DatasetFactory.create(model);

        Query consulta = QueryFactory.create(query);

        QueryExecution qexec = QueryExecutionFactory.create(consulta, dataset);
        ResultSet resultado = qexec.execSelect();
        if (!resultado.hasNext()) {
            JSONArray results = new JSONArray();
            JSONObject output = new JSONObject();
            output.put("output", results);
            output.put("query", query);
            return output.toString();
        }
        while (resultado.hasNext()) {
            HashMap<String, String> sublist = new HashMap<String, String>();
            QuerySolution tuple = (QuerySolution) resultado.next();
            Iterator iterator = tuple.varNames();

            while (iterator.hasNext()) {
                String var = (String) iterator.next();
                sublist.put(var, tuple.get(var).toString());
            }
            list.add(sublist);
        }
        JSONArray results = new JSONArray();
        for (HashMap<String, String> map : list) {
            JSONObject line = new JSONObject();
            for (String key : map.keySet()) {
                line.put(key, map.get(key));
            }
            results.put(line);
        }
        JSONObject output = new JSONObject();
        output.put("output", results);
        output.put("query", query);
        return output.toString();
    }
}
