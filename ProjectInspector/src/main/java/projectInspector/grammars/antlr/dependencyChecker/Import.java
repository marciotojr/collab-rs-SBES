/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.dependencyChecker;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author 
 */
public abstract class Import {

    protected String dependency;
    protected String assignedVariable;

    public Import(ParserRuleContext context) {
        autoSetDependency(context);
        autoSetAssignedVariable(context);
    }

    public Import() {
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getAssignedVariable() {
        return assignedVariable;
    }

    public void setAssignedVariable(String assignedVariable) {
        this.assignedVariable = assignedVariable;
    }
    
    protected abstract void autoSetDependency(ParserRuleContext context);

    protected abstract void autoSetAssignedVariable(ParserRuleContext context);
}
