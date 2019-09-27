/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.blame;

import projectInspector.grammars.antlr.code.JavaScriptLexer;
import projectInspector.grammars.antlr.code.JavaScriptParser;
import projectInspector.grammars.antlr.dependencyChecker.DependencyChecker;
import projectInspector.grammars.antlr.dependencyChecker.Usage;
import projectInspector.grammars.antlr.dependencyChecker.js.JavaScriptDependencyChecker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 *
 * @author 
 */
public class Blamer {

    Repository repository;

    DependencyChecker checker;

    String file;

    ArrayList<Usage> usages;

    Blamer(Repository repo, DependencyChecker checker, String file) {
        this.repository = repo;
        usages = checker.getUsages();
        this.file = file;
    }

    private HashMap<Usage, String> search() {
        HashMap<Usage, String> map = new HashMap<Usage, String>();
        try {
            BlameCommand blamer = new BlameCommand(repository);
            ObjectId commitID = repository.resolve("HEAD~~");
            blamer.setStartCommit(commitID);
            blamer.setFilePath(file);
            BlameResult blame = blamer.call();

            for (Usage usage : usages) {
                RevCommit commit = blame.getSourceCommit(usage.getLine());
                map.put(usage, commit.getAuthorIdent().getEmailAddress());
            }

        } catch (Exception e) {
        } finally {
            return map;
        }
    }

    public static HashMap<Usage, String> search(String path, String filePath) throws IOException {
        String fullPath = path+"/"+filePath;
        ANTLRFileStream fileStream = new ANTLRFileStream(fullPath);
        JavaScriptLexer lexer = new JavaScriptLexer(fileStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();

        JavaScriptDependencyChecker visitor = new JavaScriptDependencyChecker();

        visitor.visit(tree);
     
        Repository repo = FileRepositoryBuilder.create(new File(path+"/.git"));
        Blamer blame = new Blamer(repo, visitor, filePath);
        return blame.search();
    }

}
