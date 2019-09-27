package anonymous.collabrs.ontologyAccess.ontologyQuerying;

import anonymous.collabrs.utils.PopulatingNameFactory;
import anonymous.collabrs.utils.URIFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

/**
 *
 * @author marci
 */
public class OntologyPopulator {

    private final String baseURI;

    private OntModel ontModel;

    private String outputFile;

    private boolean isModified;

    private static Map<String, String> prefixMap = new HashMap<>();

    public OntologyPopulator(String path, String fileName, String baseURL) {
        OntologyAccess oa = new OntologyAccess(path, fileName);
        if (!baseURL.endsWith("#")) {
            baseURL = baseURL + "#";
        }
        this.baseURI = baseURL;
        ontModel = oa.getOntModel();
        savePrefixes(ontModel.getNsPrefixMap());
        isModified = false;
    }

    public OntologyPopulator(String path, String baseURL) {
        OntologyAccess oa = new OntologyAccess(path);
        if (!baseURL.endsWith("#")) {
            baseURL = baseURL + "#";
        }
        this.baseURI = baseURL;
        ontModel = oa.getOntModel();
        savePrefixes(ontModel.getNsPrefixMap());
        isModified = false;
    }

    public static void savePrefixes(Map<String, String> map) {
        for (String key : map.keySet()) {
            if (!key.equals("")) {
                prefixMap.put(key, map.get(key));
            }
        }
    }

    public Individual getIndividual(String individualName) {
        individualName = URIFormatter.format(individualName);
        return ontModel.getIndividual(baseURI + individualName);
    }

    public ObjectProperty getObjectProperty(String propertyName) {
        return ontModel.getObjectProperty(baseURI + propertyName);
    }

    public DatatypeProperty getDataProperty(String propertyName) {
        return ontModel.getDatatypeProperty(baseURI + propertyName);
    }

    public void setObjectProperty(String domainIndividual, String rangeIndividual, String propertyName) {
        if (domainIndividual == null || rangeIndividual == null || propertyName == null) {
            return;
        }
        Individual domain = getIndividual(domainIndividual);
        Individual range = getIndividual(rangeIndividual);
        ObjectProperty property = getObjectProperty(propertyName);
        domain.addProperty(property, range);
        isModified = true;
    }

    public void setStringProperty(String domainIndividual, String value, String propertyName) {
        if (domainIndividual == null || value == null || propertyName == null) {
            return;
        }
        Individual domain = getIndividual(domainIndividual);
        DatatypeProperty property = getDataProperty(propertyName);
        domain.addLiteral(property, value);
        isModified = true;
    }

    public void setIntegerProperty(String domainIndividual, int value, String propertyName) {
        if (domainIndividual == null || propertyName == null) {
            return;
        }
        Individual domain = getIndividual(domainIndividual);
        DatatypeProperty property = getDataProperty(propertyName);
        domain.addLiteral(property, (long) value);
        isModified = true;
    }

    public Individual createIndividual(String individualName, String className) {
        if (individualName == null || className == null) {
            return null;
        }
        className = URIFormatter.format(className);
        individualName = URIFormatter.format(individualName);
        Resource oclass = ontModel.getResource(baseURI + className);
        Individual individual = null;
        individual = ontModel.getIndividual(individualName);
        if (individual != null) {
            return individual;
        }
        individual = ontModel.createIndividual(baseURI + individualName, oclass);
        isModified = true;
        return individual;
    }

    public String saveOntology(String fileName) {
        OutputStream out;
        try {
            fileName = new File(fileName).getAbsolutePath();
            Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
            reasoner = reasoner.bindSchema(ontModel);
            OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_TRANS_INF;
            ontModelSpec.setReasoner(reasoner);
            outputFile = PopulatingNameFactory.generateName(fileName);
            out = new FileOutputStream(outputFile);
            ontModel.write(out, "RDF/XML-ABBREV");
            out.close();
            return outputFile;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OntologyPopulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OntologyPopulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OntModel getModel() {
        return ontModel;
    }

    public String getOutputPath() {
        return outputFile;
    }

    public boolean isIsModified() {
        return isModified;
    }

    public static Map<String, String> getPrefixMap() {
        return prefixMap;
    }
    
    public void set(OntModel ontModel){
        this.ontModel = ontModel;
    }

}
