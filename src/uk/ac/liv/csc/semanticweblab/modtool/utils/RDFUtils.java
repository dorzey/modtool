package uk.ac.liv.csc.semanticweblab.modtool.utils;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class RDFUtils {
	public static <N> boolean checkAnon(N n) {
		if (n instanceof Resource) {
			return ((Resource) n).isAnon();
		}
		// FIXME needs handling for OWL API
		return false;
	}

	/**
	 * set of default namespaces such as owl, rdf...
	 */
	public static final Set<String> defaultNamespaces = new HashSet<String>(
			Arrays.asList(new String[] { OWL.getURI(), RDF.getURI(),
					RDFS.getURI(), XSD.getURI() }));
	/** standard prefixes for sparql queries */
	public static final String prefixes = "PREFIX rdf: <" + RDF.getURI()
			+ ">\n" + "PREFIX owl: <" + OWL.getURI() + ">\nPREFIX rdfs: <"
			+ RDFS.getURI() + ">\nPREFIX xsd: <" + XSD.getURI() + ">\n";

	public static boolean isInDefaultNamespace(RDFNode n) {
		if (n.isURIResource()
				&& defaultNamespaces.contains(((Resource) n).getNameSpace())) {
			return true;
		}
		return false;
	}

	public static boolean isInDefaultNamespace(String s) {
		RDFNode n = ResourceFactory.createResource(s);
		if (n.isURIResource()
				&& defaultNamespaces.contains(((Resource) n).getNameSpace())) {
			return true;
		}
		return false;
	}

	public static Set<OntClass> namedClassesNotInDN(OntModel model) {
		List<OntClass> classList = model.listNamedClasses().toList();
		for (int index = 0; index < classList.size();) {
			if (isInDefaultNamespace(classList.get(index))) {
				classList.remove(index);
			} else {
				index++;
			}
		}
		
		Set<OntClass> classSet = new HashSet<OntClass>(classList);
		return classSet;
	}

	public static boolean URIResourceAndNotInDefaultNS(RDFNode n) {
		return n.isURIResource()
				&& !defaultNamespaces.contains(((Resource) n).getNameSpace());
	}

}
