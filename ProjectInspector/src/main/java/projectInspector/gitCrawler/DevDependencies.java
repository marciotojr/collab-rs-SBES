/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.gitCrawler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author 
 */
public class DevDependencies {

    File file;

    public DevDependencies(File file) {
        this.file = file;
    }

    ArrayList<String> getDependencies() {
        ArrayList<String> dep = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();

            String content = sb.toString();
            JSONObject obj = new JSONObject(content);
            JSONObject d = obj.getJSONObject("dependencies");
            JSONObject dd = obj.getJSONObject("devDependencies");
            try {
                for (String s : d.keySet()) {
                    dep.add(s);
                }
            } catch (Exception e) {

            }
            try {
                for (String s : dd.keySet()) {
                    dep.add(s);
                }
            } catch (Exception e) {

            }
        } catch (Exception ex) {

        }
        return dep;
    }

}
