/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.dependencyChecker;

/**
 *
 * @author 
 */
public class Usage {
    Import importStatement;
    int line;

    public Usage(Import importStatement, int line) {
        this.importStatement = importStatement;
        this.line = line;
    }

    public Import getImportStatement() {
        return importStatement;
    }

    public void setImportStatement(Import importStatement) {
        this.importStatement = importStatement;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    
}

