package collabrs_recommender.ontologyQuerying;

/**
 *
 * @author marci
 */
public class OntologyPopulator extends OntologyServiceAccess {

    static OntologyPopulator instance;

    public String getIndividual(String individualName) {
        return getContent("/individual?subject=" + individualName);
    }

    public String getObjectProperty(String propertyName) {
        return getContent("/objectproperty?property=" + propertyName);
    }

    public String getDataProperty(String propertyName) {
        return getContent("/dataproperty?property=" + propertyName);
    }

    public String setObjectProperty(String domainIndividual, String rangeIndividual, String propertyName) {
        return getContent("/setobjectproperty?property=" + propertyName + "&subject=" + domainIndividual + "&object=" + rangeIndividual);
    }

    public String setStringProperty(String domainIndividual, String value, String propertyName) {
        return getContent("/setstringproperty?property=" + propertyName + "&subject=" + domainIndividual + "&object=" + value);
    }

    public String setIntegerProperty(String domainIndividual, int value, String propertyName) {
        return getContent("/setintegerproperty?property=" + propertyName + "&subject=" + domainIndividual + "&object=" + value);
    }

    public String createIndividual(String individualName, String className) {
        return getContent("/setintegerproperty?subject=" + individualName + "&class=" + className);
    }

    public String saveOntology() {
        return getContent("/getowl");
    }
    
    public String executeReasoner() {
        return getContent("/executereasoner");
    }

    public static OntologyPopulator getInstance() {
        if (instance == null) {
            instance = new OntologyPopulator();
        }
        return instance;
    }

}
