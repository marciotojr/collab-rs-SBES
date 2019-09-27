/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs;

import anonymous.collabrs.ontologyAccess.ontologyQuerying.OntologyPopulator;

/**
 *
 * @author
 */
public class InstanceHandler {

    public static final String BASE_URL = "https://anonymous.anonymous.anonymous/SECOn.owl";
    private static final String PATH = "SECOn.owl";
    private static OntologyPopulator OP = new OntologyPopulator(PATH, BASE_URL);
    
    public static OntologyPopulator getPopulator(){
        return OP;
    }
    
    
}
