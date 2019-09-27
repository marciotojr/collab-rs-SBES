/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector;

import projectInspector.grammars.antlr.myVisitor.JavaVisitor;
import projectInspector.grammars.antlr.code.JavaLexer;
import projectInspector.grammars.antlr.code.JavaParser;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author 
 */
public class ParserJava {

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/br/ufjf/merge/projectscan/examples/JavaExample.java";

        ANTLRFileStream fileStream = new ANTLRFileStream(path);
        JavaLexer lexer = new JavaLexer(fileStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        //GUI 
        JFrame frame = new JFrame("Java parser");
        JPanel panel = new JPanel();

        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.open();

        frame.add(viewer);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JavaVisitor visitor = new JavaVisitor();

        visitor.visit(tree);

    }

}
