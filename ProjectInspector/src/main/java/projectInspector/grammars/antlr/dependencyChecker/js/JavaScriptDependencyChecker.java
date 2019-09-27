/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.dependencyChecker.js;

import projectInspector.grammars.antlr.code.JavaScriptParser;
import projectInspector.grammars.antlr.code.JavaScriptParserBaseVisitor;
import projectInspector.grammars.antlr.dependencyChecker.DependencyChecker;
import projectInspector.grammars.antlr.dependencyChecker.Import;
import projectInspector.grammars.antlr.dependencyChecker.Usage;
import java.util.ArrayList;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author 
 */
public class JavaScriptDependencyChecker extends JavaScriptParserBaseVisitor<Object> implements DependencyChecker {

    ArrayList<Import> imports;
    ArrayList<Usage> usages;

    public JavaScriptDependencyChecker() {
        this.imports = new ArrayList<Import>();
        this.usages = new ArrayList<Usage>();
    }

    public void logUsages(Import importStatement, int line) {
        usages.add(new Usage(importStatement, line));
    }

    public void logImports(ParserRuleContext ctx) {
        if (ctx.getText().contains("/") || ctx.getText().contains(".")) {
            return;
        }
        Import imp = new JavaScriptImport(ctx);
        imports.add(imp);
        logUsages(imp, ctx.getStart().getLine());

    }

    @Override
    public Object visitArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) {

        for (ParseTree child : ctx.getParent().children) {
            if (child.getChildCount() > 1) {
                if (child.getChild(0).getText().equals("require")) {
                    logImports(ctx);
                }
            }
        }
        return visitChildren(ctx);
    }

    public ArrayList<Import> getImports() {
        return imports;
    }

    @Override
    public Object visitStatement(JavaScriptParser.StatementContext ctx) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<ParseTree> elements = new ArrayList<ParseTree>();
        if (ctx.children != null) {
            for (ParseTree child : ctx.children) {
                elements.add(child);
            }
        }
        ArrayList<ParseTree> removables, addabales;
        do {
            removables = new ArrayList<ParseTree>();
            addabales = new ArrayList<ParseTree>();
            for (ParseTree element : elements) {
                if (element.getChildCount() > 0) {
                    removables.add(element);
                    for (int i = 0; i < element.getChildCount(); i++) {
                        addabales.add(element.getChild(i));
                    }
                    for (Import imp : imports) {
                        String s = imp.getAssignedVariable();
                        if (element.getText().equals(s)) {
                            logUsages(imp, ctx.getStart().getLine());
                            return visitChildren(ctx);
                        }
                    }

                }
            }
            for (ParseTree remove : removables) {
                elements.remove(remove);
            }
            for (ParseTree add : addabales) {
                elements.add(add);
            }
            list = new ArrayList<String>();
            for (ParseTree e : elements) {
                list.add(e.getText());
            }
            System.out.print("");
        } while (removables.size() > 0);

        for (ParseTree child : elements) {
            for (Import imp : imports) {
                String importation = imp.getAssignedVariable();
                String ctxs, childs;
                ctxs = ctx.getText();
                childs = child.getText();
                if (child.getText().equals(importation)) {
                    logUsages(imp, ctx.getStart().getLine());
                }
            }
        }

        return visitChildren(ctx);
    }

    public Import visitBlockStatements(ParserRuleContext ctx) {
        for (ParseTree child : ctx.children) {
            if (!(child.getText().contains("{") || child.getText().contains("}") || child.getText().equals("while") || child.getText().equals("for") || child.getText().equals("do") || child.getText().equals("if"))) {
                for (Import imp : imports) {
                    if (child.getText().contains(imp.getAssignedVariable())) {
                        return imp;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object visitDoStatement(JavaScriptParser.DoStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStop().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitIfStatement(JavaScriptParser.IfStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitForStatement(JavaScriptParser.ForStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitForVarStatement(JavaScriptParser.ForVarStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitForInStatement(JavaScriptParser.ForInStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitForVarInStatement(JavaScriptParser.ForVarInStatementContext ctx) {
        Import imp = visitBlockStatements(ctx);
        if (imp != null) {
            logUsages(imp, ctx.getStart().getLine());
        }
        return visitChildren(ctx);
    }

    public ArrayList<Usage> getUsages() {
        return usages;
    }

}
