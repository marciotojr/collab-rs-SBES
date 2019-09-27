/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.requester;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author 
 */
public abstract class GETRequester {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String getContent(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //con.setRequestProperty("Authorization", USER_TOKEN);

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        if (responseCode == 403 || responseCode == 404) {
            con.disconnect();
            throw new Exception();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response.toString();

    }
   
}
