/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.myVisitor;

import projectInspector.grammars.antlr.code.JavaScriptParser;
import projectInspector.grammars.antlr.code.JavaScriptParserBaseVisitor;
import static projectInspector.grammars.antlr.myVisitor.JavaVisitor.log;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author 
 */
public class JavaScriptVisitor extends JavaScriptParserBaseVisitor<Object> {

    
    public static void log(ParserRuleContext ctx) {
        Token start = ctx.getStart();
        Token stop = ctx.getStop();
        System.out.println(Thread.currentThread().getStackTrace()[2].toString()
                + " \n\tLine begin= " + start.getLine()
                + " \n\tLine end =" + stop.getLine()
                + " \n\tColumn begin =" + (start.getCharPositionInLine() + 1) + " - " + (start.getCharPositionInLine() + start.getText().length())
                + " \n\tColumn end =" + (stop.getCharPositionInLine() + 1) + " - " + (stop.getCharPositionInLine() + stop.getText().length())
                + "\n\tText:"
                + "\n\t\t" + ctx.getText()
                + "\n\n"
        );
    }

    @Override
    public Object visitArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) {

        for (ParseTree child : ctx.getParent().children) {
            if (child.getChildCount() > 1) {
                if (child.getChild(0).getText().equals("require")) {
                    log(ctx);
                }
            }
        }
        return visitChildren(ctx);
    }

}
