/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector;

import projectInspector.grammars.antlr.dependencyChecker.js.JavaScriptDependencyChecker;
import projectInspector.grammars.antlr.code.JavaScriptLexer;
import projectInspector.grammars.antlr.code.JavaScriptParser;
import projectInspector.grammars.antlr.dependencyChecker.Import;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import projectInspector.grammars.antlr.dependencyChecker.Usage;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.antlr.v4.gui.TreeViewer;

/**
 *
 * @author 
 */
public class ParserJavaScript {

    public static void main(String[] args) throws IOException {
        String path = "/path/to/file";

        ANTLRFileStream fileStream = new ANTLRFileStream(path);
        JavaScriptLexer lexer = new JavaScriptLexer(fileStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParseTree tree = parser.program();

        //GUI 
        JFrame frame = new JFrame("JavaScript parser");
        JPanel panel = new JPanel();

        /*TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.open();

        frame.add(viewer);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        JavaScriptDependencyChecker visitor = new JavaScriptDependencyChecker();

        visitor.visit(tree);

        ArrayList<Import> imports = visitor.getImports();
        ArrayList<Usage> usages = visitor.getUsages();

        for (Import ctx : imports) {
            //System.out.println(ctx.getAssignedVariable() + " ==> " + ctx.getDependency());
        }
        for (Usage prc : visitor.getUsages()) {
            //System.out.println(prc.getContext().getText());
        }
        System.out.println("");

    }

}
