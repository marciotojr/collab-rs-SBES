package collabrs_recommender;

import collabrs_recommender.ontologyQuerying.OntologyPopulator;
import collabrs_recommender.utils.ThreadManager;
import collabrs_recommender.recommendation.Recommender;
import collabrs_recommender.utils.CommandThreadManager;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.CommandService;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.FollowersListService;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.OwnedRepoService;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.UserService;

public class OntolibApplication {

    private static final String BASE_URL = "https://anonymous.anonymous.anonymous/SECOn.owl";
    private static final String PATH = "SECOn.owl";
    public static final CommandThreadManager THREAD_MANAGER = CommandThreadManager.getInstance();
    public static ThreadManager repoWalkers = new ThreadManager("Dependency walker");
    public static final String TARGET_PROJECT = "webpack/webpack";

    public static void main(String[] args) {
        try {
            new Thread(repoWalkers).start();
            
            
            CommandService thread = new UserService();
            THREAD_MANAGER.addThread("user", thread);

//            thread = new RepositoryService(op);
//            THREAD_MANAGER.addThread("repo", thread);
//            thread.getQueue().put(TARGET_PROJECT);

            thread = new FollowersListService();
            THREAD_MANAGER.addThread("followers", thread);
            thread.getQueue().put(TARGET_PROJECT);

            thread = new OwnedRepoService();
            THREAD_MANAGER.addThread("owned", thread);

            for (String s : THREAD_MANAGER.keySet()) {
                THREAD_MANAGER.start(s);
            }

            boolean allHalting;
            do {
                allHalting = true;
                for (String s : THREAD_MANAGER.keySet()) {
                    Thread.sleep(100);
                    allHalting = allHalting && (THREAD_MANAGER.getCommandService(s).isHalting() || !THREAD_MANAGER.isAlive(s));
                }
                if (allHalting) { //double check
                    allHalting = true;
                    for (String s : THREAD_MANAGER.keySet()) {
                        allHalting = allHalting && (THREAD_MANAGER.getCommandService(s).isHalting() || !THREAD_MANAGER.isAlive(s));
                    }
                }
            } while (!allHalting);
            
            for (String s : THREAD_MANAGER.keySet()) {
                    THREAD_MANAGER.interrupt(s);
            }
          
            while(repoWalkers.isAlive()){
                Thread.sleep(1000);
            }
            repoWalkers.interrupt();
//            while (repoWalkers.isAlive()) {
//                
//            }
//            repoWalkers.interrupt();

            for (String s : THREAD_MANAGER.keySet()) {
                THREAD_MANAGER.interrupt(s);
            }
            //Thread.sleep(1000*60*60);
            
            OntologyPopulator.getInstance().executeReasoner();
            Recommender ranking = new Recommender(TARGET_PROJECT);
            ranking.recommend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
