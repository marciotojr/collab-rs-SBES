/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.dependencyChecker.js;

import projectInspector.grammars.antlr.dependencyChecker.Import;
import projectInspector.grammars.antlr.dependencyChecker.Usage;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author 
 */
public class JavaScriptImport extends Import {
    
    public JavaScriptImport(ParserRuleContext context){
        super(context);
    }

    @Override
    protected void autoSetDependency(ParserRuleContext context) {
        this.setDependency(context.getChild(1).getChild(1).getText());
    }

    @Override
    protected void autoSetAssignedVariable(ParserRuleContext context) {
        this.setAssignedVariable(context.getParent().getChild(0).getText());
    }

}
