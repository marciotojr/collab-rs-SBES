/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.ontologyQuerying;

import static collabrs_recommender.ontologyQuerying.OntologyPopulator.instance;

/**
 *
 */
public class OntologyQuery extends OntologyServiceAccess {

    static OntologyQuery instance;

    public String executeQuery(String query) {
        return getContent("/?q=" + query);
    }

    public static OntologyQuery getInstance() {
        if (instance == null) {
            instance = new OntologyQuery();
        }
        return instance;
    }
}
