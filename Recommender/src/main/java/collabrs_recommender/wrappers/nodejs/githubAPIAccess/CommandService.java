package collabrs_recommender.wrappers.nodejs.githubAPIAccess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import collabrs_recommender.ontologyQuerying.OntologyPopulator;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.NoRemainingRequestsException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.exception.PageForbiddenException;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.queueController.QueueController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.SSLHandshakeException;

/**
 *
 * @author marci
 */
public abstract class CommandService implements Runnable {

    private static final String USER_AGENT = "Mozilla/5.0";
    public static OntologyPopulator op = OntologyPopulator.getInstance();
    private static String USER_TOKEN;
    private static ArrayList<String> TOKEN_LIST = null;
    protected QueueController queue;
    boolean halting;
    boolean breakFlag;

    public CommandService() {
        if (TOKEN_LIST == null) {
            TOKEN_LIST = new ArrayList<String>();
            TOKEN_LIST.add("token 1b3ae1ee6fe0660bbb2af60e475f4b0326ea6001");
        }
        if (USER_TOKEN == null) {
            renewToken();
        }

        this.op = op;
        this.queue = new QueueController();
        halting = false;
        breakFlag = false;
    }

    public QueueController getQueue() {
        return queue;
    }

    public void setBreakFlag(boolean breakFlag) {
        this.breakFlag = breakFlag;
    }

    public void run() {
        initialize();
        while (true) {
            try {
                String object = queue.pop();
                if (breakFlag) {
                    break;
                }
                if (object != null) {
                    halting = false;
                    getData(object);
                } else {
                    halting = true;
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
                increment();
            } catch (NoRemainingRequestsException e) {
                halting = true;
                queue = new QueueController();
                break;

            } catch (PageForbiddenException e) {
                increment();
            } catch (Exception e) {
                try {
                    Thread.sleep(1000 * 20);
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    public boolean isHalting() {
        if (!queue.isEmpty()) {
            halting = false;
        }
        return halting;
    }

    abstract public void increment();

    abstract public void initialize();

    abstract public void getData(String object) throws PageForbiddenException, NoRemainingRequestsException;

    public static String getContent(String url) throws Exception {

        URL obj;
        int responseCode;
        HttpURLConnection con;

        while (true) {
            try {
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("Authorization", USER_TOKEN);

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

        int limit = new Integer(con.getHeaderField("X-RateLimit-Remaining"));

        if (limit <= 0) {
            if (renewToken()) {
                return getContent(url);
            }
            con.disconnect();
            throw new NoRemainingRequestsException();
        }

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

    }

    private static boolean renewToken() {
        if (TOKEN_LIST != null && TOKEN_LIST.size() > 0) {
            USER_TOKEN = TOKEN_LIST.get(0);
            TOKEN_LIST.remove(0);
            return true;
        }
        return false;
    }
}
