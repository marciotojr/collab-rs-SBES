package collabrs_recommender.ontologyReasoning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Helper class for extracting labels, comments and other annotations in
 * preferred languages. Selects the first literal annotation matching the given
 * languages in the given order.
 */
@SuppressWarnings("WeakerAccess")
public class LocalizedAnnotationSelector {

    private final List<String> langs;
    private final OWLOntology ontology;
    private final OWLDataFactory factory;

    /**
     * Constructor.
     *
     * @param ontology ontology
     * @param factory data factory
     * @param langs list of preferred languages; if none is provided the
     * Locale.getDefault() is used
     */
    public LocalizedAnnotationSelector(OWLOntology ontology, OWLDataFactory factory, String... langs) {
        this.langs = (langs == null) ? Collections.singletonList(Locale.getDefault().toString()) : Arrays.asList(langs);
        this.ontology = ontology;
        this.factory = factory;
    }

    /**
     * Provides the first label in the first matching language.
     *
     * @param ind individual
     * @return label in one of preferred languages or null if not available
     */
    public String getLabel(OWLNamedIndividual ind) {
        return getAnnotationString(ind, OWLRDFVocabulary.RDFS_LABEL.getIRI());
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getComment(OWLNamedIndividual ind) {
        return getAnnotationString(ind, OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    }

    public String getAnnotationString(OWLNamedIndividual ind, IRI annotationIRI) {
        return getLocalizedString(EntitySearcher.getAnnotations(ind, ontology, factory.getOWLAnnotationProperty(annotationIRI)));
    }

    private String getLocalizedString(Collection<OWLAnnotation> annotations) {
        List<OWLLiteral> literalLabels = new ArrayList<OWLLiteral>();
        for (OWLAnnotation label : annotations) {
            if (label.getValue() instanceof OWLLiteral) {
                literalLabels.add((OWLLiteral) label.getValue());
            }
        }
        for (String lang : langs) {
            for (OWLLiteral literal : literalLabels) {
                if (literal.hasLang(lang)) {
                    return literal.getLiteral();
                }
            }
        }
        for (OWLLiteral literal : literalLabels) {
            if (!literal.hasLang()) {
                return literal.getLiteral();
            }
        }
        return null;
    }
}
