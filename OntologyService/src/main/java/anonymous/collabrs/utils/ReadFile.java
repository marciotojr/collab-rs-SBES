/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author
 */
public class ReadFile {

    public static String read(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String output = sb.toString();
            br.close();
            return output;
        } catch (Exception e) {
            return "";
        }
    }
}
