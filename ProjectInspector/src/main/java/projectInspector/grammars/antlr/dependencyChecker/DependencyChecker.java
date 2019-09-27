/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.dependencyChecker;

import java.util.ArrayList;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author 
 */
public interface DependencyChecker {
    public ArrayList<Usage> getUsages();
    public ArrayList<Import> getImports();  
}
