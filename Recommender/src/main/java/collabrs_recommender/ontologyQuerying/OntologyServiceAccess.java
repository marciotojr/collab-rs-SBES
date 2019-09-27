/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.ontologyQuerying;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.SSLHandshakeException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;

/**
 *
 * @author marcio
 */
public abstract class OntologyServiceAccess {
    
    public static final String URL_PREFIX = "http://localhost:8080";

    public String getContent(String url) {
        try {
            url = URL_PREFIX + url;
            URL obj;
            int responseCode;
            HttpURLConnection con;

            while (true) {
                try {
                    obj = new URL(url);
                    con = (HttpURLConnection) obj.openConnection();

                    // optional default is GET
                    con.setRequestMethod("GET");
                    responseCode = con.getResponseCode();
                    break;
                } catch (SSLHandshakeException e) {
                    System.out.println("An SSLHandshakeException occurred. Retrying connection!");
                }
            }
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            System.out.println("Tentativas restantes : " + con.getHeaderField("X-RateLimit-Remaining"));

            if (responseCode == 403 || responseCode == 404 || responseCode == 401) {
                con.disconnect();
                throw new PageForbiddenException();
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

        } catch (Exception e) {
            return "";
        }
    }
}
