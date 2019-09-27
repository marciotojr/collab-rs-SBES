/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.ontologyAccess.services;

import anonymous.collabrs.InstanceHandler;
import anonymous.collabrs.ontologyAccess.ontologyQuerying.OntologyAccess;
import anonymous.collabrs.ontologyAccess.ontologyQuerying.OntologyPopulator;
import anonymous.collabrs.ontologyAccess.ontologyQuerying.OntologyQuery;
import anonymous.collabrs.ontologyAccess.ontologyReasoning.OntologyReasoner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OntologyQueryController {

    @RequestMapping("/executereasoner")
    public String executeReasoner() {
        try {
            OntologyPopulator op = InstanceHandler.getPopulator();
            if (op.isIsModified()) {
                String path = op.saveOntology("reasoned.owl");
                OntologyReasoner oh = new OntologyReasoner(InstanceHandler.BASE_URL, path);
                OntologyAccess oa = new OntologyAccess(oh.getOutputPath());
                op.set(oa.getOntModel());
                return "Resoner executed successfully";
            }
            return "Resoner was not necessary";
        } catch (Exception e) {
            return "Resoner failed";
        }
    }

    @RequestMapping("/")
    public String query(@RequestParam(value = "q", defaultValue = "") String query) {
        try {
            OntologyPopulator op = InstanceHandler.getPopulator();
            String output = OntologyQuery.executeQuery(query, op.getModel());
            return output;
        } catch (Exception e) {
            return "{output:{}}";
        }
    }

}
