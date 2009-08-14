package uk.ac.liv.csc.semanticweblab.modtool.modularisation;

import java.util.Iterator;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ValidityReport;

/**
 * Takes an ontology file as input and checks that it is clean and valid. It
 * uses the Pellet reasoner to perform these functions.
 * 
 * The code from this class was adapted from the JenaReasoner.java example
 * provided with the Pellet distribution.
 * 
 * @author Paul Doran
 */
public class OntologyValidator {
	private OntModel module;
	private Iterator<?> errors;

	public OntologyValidator(OntModel toCheck) {
		this.module = ModelFactory
				.createOntologyModel(PelletReasonerFactory.THE_SPEC);
		this.module.add(toCheck);
	}

	/**
	 * This methods tests the {@link #module module}. Please note this method
	 * should only be called after the constructor has been.
	 * 
	 * @return true is valid module false otherwise
	 */
	public boolean validateModule() {
		// print validation report
		ValidityReport report = this.module.validate();
		if (report.isClean() && report.isValid()) {
			return true;
		}
		this.errors = report.getReports();
		return false;
	}// End validateModule()

	/**
	 * Returns an Iterator of all the errors that may have occured.
	 * 
	 * @return errors
	 */
	public Iterator<?> getErrorReports() {
		return this.errors;
	}
}// End class OntologyValidator
