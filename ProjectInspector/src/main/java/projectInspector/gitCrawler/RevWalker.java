/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.gitCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author 
 */
public class RevWalker {

    public void reset(Repository repo, String hash) throws IOException {
        Git git = new Git(repo);
        ResetCommand reset = git.reset();
        reset.setRef(hash);
        reset.setMode(ResetType.HARD);
        try {
            Ref resetRef = reset.call();
            if (resetRef != null) {
                System.out.println(
                        "Reset label to version " + resetRef.getObjectId());
            }
            return;//resetRef;
        } catch (Exception ex) {
            String message = "Could not reset to remote for " + " (current ref="
                    + "), remote: " + git.getRepository().getConfig()
                    .getString("remote", "origin", "url");
            System.out.println(message);
            return;// null;
        }

    }

    public Collection<String> getHashes(Repository repo) throws IOException, GitAPIException {
        ArrayList<String> hashes = new ArrayList<String>();
        Git git = new Git(repo);
        RevWalk walk = new RevWalk(repo, Integer.MAX_VALUE);

        List<Ref> branches = git.branchList().call();

        for (Ref branch : branches) {
            String branchName = branch.getName();

            System.out.println("Commits of branch: " + branch.getName());
            System.out.println("-------------------------------------");

            Iterable<RevCommit> commits = git.log().all().call();

            for (RevCommit commit : commits) {
                boolean foundInThisBranch = false;

                RevCommit targetCommit = walk.parseCommit(repo.resolve(
                        commit.getName()));
                for (Map.Entry<String, Ref> e : repo.getAllRefs().entrySet()) {
                    if (e.getKey().startsWith(Constants.R_HEADS)) {
                        if (walk.isMergedInto(targetCommit, walk.parseCommit(
                                e.getValue().getObjectId()))) {
                            String foundInBranch = e.getValue().getName();
                            if (branchName.equals(foundInBranch)) {
                                foundInThisBranch = true;
                                break;
                            }
                        }
                    }
                }

                if (foundInThisBranch) {
                    hashes.add(commit.getId().getName());
                    System.out.println(commit.getId().getName());
                    break;
                }
            }
        }/**/

        return hashes;
    }

    public void historicAnalysis(String path) throws IOException, GitAPIException {
        /*GithubCloner gc = new GithubCloner();
        gc.gitClone(path);/**/
        path = "temp/" + path;
        Walker fw = new Walker(path);
        Repository repo = new FileRepository(path+ "/.git");
        Collection<String> hashes = getHashes(repo);
        int counter = 0;
        for (String hash : hashes) {
            counter++;
            System.out.println("Commit: " + counter + "/" + hashes.size()+ " Hash: "+hash);
            reset(repo, hash);
//            reset(repo, hash);
            fw.walk();
            if(counter>0)break;
        }
        HashSet<String> lib = new HashSet<String>();
        HashSet<String> users = new HashSet<String>();
        for (String email : fw.getKnowledge().keySet()) {
            users.add(email);
            HashSet<String> dependencies = fw.getKnowledge().get(email);
            for (String dependency : dependencies) {
                lib.add(dependency);
                System.out.println(email + ", " + dependency);
            }
        }
        for(String dep:lib){
            System.out.println(dep);
        }
        System.out.println("\n\n");
        for (String email : fw.getKnowledge().keySet()) {
            users.add(email);
            HashSet<String> dependencies = fw.getKnowledge().get(email);
            System.out.println(email+", "+(float)dependencies.size()/(float)lib.size());
        }
        
    }

    public void headAnalysis(String path) throws IOException, GitAPIException {
        /*GithubCloner gc = new GithubCloner();
        gc.gitClone(path);*/
        path = "temp/" + path + "/.git";
        Walker fw = new Walker(path);
        Repository repo = new FileRepository(path);
//        Collection<String> hashes = getHashes(repo);

        fw.walk();
        for (String email : fw.getKnowledge().keySet()) {
            HashSet<String> dependencies = fw.getKnowledge().get(email);
            for (String dependency : dependencies) {
                System.out.println(email + " -> " + dependency);
            }
        }
    }

    public static void main(String[] args) {
        try {
            //reset("62a59b6ace6c1cb34f9adf2d433ac57d3c826ce4");
            /*/for(String hash:getHashes(true)){
             System.out.println(hash);
             }*/
            RevWalker rv = new RevWalker();
            rv.historicAnalysis("expressjs/express");
            /*
             Walker fw = new Walker("expressjs/express");
             fw.walk();
             for(String email:fw.getKnowledge().keySet()){
             HashSet<String> dependencies = fw.getKnowledge().get(email);
             for(String dependency: dependencies){
             System.out.println(email + " -> " + dependency);
             }
             }*/
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (GitAPIException ex) {
            ex.printStackTrace();
        }

    }
}
