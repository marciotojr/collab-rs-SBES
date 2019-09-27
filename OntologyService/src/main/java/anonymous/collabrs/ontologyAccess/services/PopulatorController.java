/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anonymous.collabrs.ontologyAccess.services;

import anonymous.collabrs.InstanceHandler;
import anonymous.collabrs.ontologyAccess.ontologyQuerying.OntologyPopulator;
import anonymous.collabrs.utils.ReadFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PopulatorController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/individual")
    public String getIndividual(@RequestParam(value = "subject") String individualName) {
        return InstanceHandler.getPopulator().getIndividual(individualName).getURI();
    }

    @RequestMapping("/objectproperty")
    public String getObjectProperty(@RequestParam(value = "property") String propertyName) {
        return InstanceHandler.getPopulator().getObjectProperty(propertyName).getURI();
    }

    @RequestMapping("/dataproperty")
    public String getDataProperty(@RequestParam(value = "property") String propertyName) {
        return InstanceHandler.getPopulator().getDataProperty(propertyName).getURI();
    }

    @RequestMapping("/setobjectproperty")
    public String setObjectProperty(@RequestParam(value = "subject") String domainIndividual, @RequestParam(value = "object") String rangeIndividual, @RequestParam(value = "property") String propertyName) {
        InstanceHandler.getPopulator().setObjectProperty(domainIndividual, rangeIndividual, propertyName);
        return "";
    }

    @RequestMapping("/setstringproperty")
    public String setStringProperty(@RequestParam(value = "subject") String domainIndividual, @RequestParam(value = "value") String value, @RequestParam(value = "property") String propertyName) {
        InstanceHandler.getPopulator().setStringProperty(domainIndividual, value, propertyName);
        return "";

    }

    @RequestMapping("/setintegerproperty")
    public String setIntegerProperty(@RequestParam(value = "subject") String domainIndividual, @RequestParam(value = "value") int value, @RequestParam(value = "property") String propertyName) {
        InstanceHandler.getPopulator().setIntegerProperty(domainIndividual, value, propertyName);
        return "";
    }

    @RequestMapping("/addindividual")
    public String createIndividual(@RequestParam(value = "subject") String individualName, @RequestParam(value = "class") String className) {
        return InstanceHandler.getPopulator().createIndividual(individualName, className).getURI();
    }

    @RequestMapping("/getowl")
    public String saveOntology() {
        return ReadFile.read(InstanceHandler.getPopulator().saveOntology("temp.owl"));
    }

}
