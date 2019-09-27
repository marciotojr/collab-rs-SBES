package anonymous.collabrs.ontologyAccess.ontologyReasoning;

import anonymous.collabrs.utils.ReasoningNameFactory;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import uk.ac.manchester.cs.owl.explanation.ordering.Tree;

import java.util.*;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

/**
 * Example how to use an OWL ontology with a reasoner.
 * <p>
 * Run in Maven with
 * <code>mvn exec:java -Dexec.mainClass=cz.makub.Tutorial</code>
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class OntologyReasoner {

    private final String BASE_URL;
    private final OWLObjectRenderer renderer;
    private final PrefixDocumentFormat pm;
    private final OWLDataFactory factory;
    private final OWLReasoner reasoner;
    private final OWLOntology ontology;
    private final OWLOntologyManager manager;
    private final OWLReasonerFactory reasonerFactory;
    private final String outputPath;

    /**
     * Starts the ontology handler using an web ontology
     *
     * @param BaseURL Location of the ontology
     * @throws OWLOntologyCreationException Throws error when ontology is not
     * accessible
     */
    public OntologyReasoner(String BaseURL, String file_path) throws OWLOntologyCreationException, OWLOntologyStorageException {
        this.BASE_URL = BaseURL;
        this.renderer = new DLSyntaxObjectRenderer();

        //prepare ontology and reasoner
        manager = OWLManager.createOWLOntologyManager();
        File file = new File(file_path);
        ontology = manager.loadOntologyFromOntologyDocument(file);
        //ontology = manager.loadOntologyFromOntologyDocument(IRI.create(BASE_URL));
        reasonerFactory = PelletReasonerFactory.getInstance();
        reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
        factory = manager.getOWLDataFactory();
        pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
        pm.setDefaultPrefix(BASE_URL + "#");
        outputPath = save(file_path);
    }

    /**
     *
     * @param node
     * @param renderer
     * @param indent
     */
    private static void printIndented(Tree<OWLAxiom> node, OWLObjectRenderer renderer, String indent) {
        OWLAxiom axiom = node.getUserObject();
        System.out.println(indent + renderer.render(axiom));
        if (!node.isLeaf()) {
            for (Tree<OWLAxiom> child : node.getChildren()) {
                printIndented(child, renderer, indent + "    ");
            }
        }
    }

    /**
     * Returns individual that belongs to the base ontology
     *
     * @param individualName Suffix for an individual URI
     * @return Individual object
     */
    public OWLNamedIndividual getIndividual(String individualName) {
        return factory.getOWLNamedIndividual(":" + individualName, pm);
    }

    /**
     * Returns class that belongs to the base ontology
     *
     * @param className Suffix for a class URI
     * @return Class object
     */
    public OWLClass getClass(String className) {
        return factory.getOWLClass(":" + className, pm);
    }

    /**
     * Returns all individuals from a class
     *
     * @param className Suffix for a class URI
     * @return An array list with objects of all individuals belonging to a
     * class
     */
    public ArrayList<OWLNamedIndividual> getIndividualsFromClass(String className) {
        OWLClass owlClass = getClass(className);

        ArrayList<OWLNamedIndividual> list = new ArrayList<OWLNamedIndividual>();
        for (OWLNamedIndividual individual : reasoner.getInstances(owlClass, false).getFlattened()) {
            list.add(individual);
        }
        return list;

    }

    /**
     * Returns all classes that a given individual belongs to
     *
     * @param individualName Suffix for an individual URI
     * @return An array list with objects of all classes an individual belongs
     * to
     */
    public ArrayList<OWLClass> getIndividualClasses(String individualName) {
        ArrayList<OWLClass> owlClasses = new ArrayList<OWLClass>();
        for (OWLClass c : reasoner.getTypes(getIndividual(individualName), false).getFlattened()) {
            owlClasses.add(c);
        }
        return owlClasses;
    }

    /**
     * Returns the object representing the given object property of the base
     * ontology
     *
     * @param proprietyName Suffix for an object property URI
     * @return Object property object
     */
    public OWLObjectProperty getObjectProperty(String proprietyName) {
        return factory.getOWLObjectProperty(":" + proprietyName, pm);
    }

    /**
     * Returns the object representing the given data property of the base
     * ontology
     *
     * @param proprietyName Suffix for an data property URI
     * @return Data property object
     */
    public OWLDataProperty getDataProperty(String proprietyName) {
        return factory.getOWLDataProperty(":" + proprietyName, pm);
    }

    /**
     * Verify if there is a given object property relating two given individuals
     *
     * @param domainIndividual Domain individual
     * @param rangeIndividual Range individual
     * @param propriety Object property to have existence verified
     * @return True if the relationship exists between both individuals, false
     * otherwise
     */
    public boolean verifyObjectPropertyExistence(String domainIndividual, String rangeIndividual, String propriety) {
        return reasoner.isEntailed(factory.getOWLObjectPropertyAssertionAxiom(getObjectProperty(propriety), getIndividual(domainIndividual), getIndividual(rangeIndividual)));
    }

    private String save(String name) throws OWLOntologyCreationException, OWLOntologyStorageException {
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
         gens.add(new InferredSubClassAxiomGenerator());  
         gens.add(new InferredClassAssertionAxiomGenerator());
         gens.add( new InferredDisjointClassesAxiomGenerator());
         gens.add( new InferredEquivalentClassAxiomGenerator());
         gens.add( new InferredEquivalentDataPropertiesAxiomGenerator());
         gens.add( new InferredEquivalentObjectPropertyAxiomGenerator());
         gens.add( new InferredInverseObjectPropertiesAxiomGenerator());
         gens.add( new InferredObjectPropertyCharacteristicAxiomGenerator());
         gens.add( new InferredPropertyAssertionGenerator());
         gens.add( new InferredSubDataPropertyAxiomGenerator());
         gens.add( new InferredSubObjectPropertyAxiomGenerator());

         InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
         OWLOntology infOnt = manager.createOntology();
         iog.fillOntology(this.factory, infOnt);
         manager.saveOntology(infOnt,new RDFXMLDocumentFormat(),IRI.create(new File(ReasoningNameFactory.generateName(name))));
         return ReasoningNameFactory.generateName(name);
    }

    public String getOutputPath() {
        return outputPath;
    }
    
}
