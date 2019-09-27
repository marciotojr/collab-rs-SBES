/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 */
public class SaveToFile {

    public static void SaveFile(String fileName, String str) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(str);

            writer.close();
        } catch (Exception e) {

        }
    }
}
