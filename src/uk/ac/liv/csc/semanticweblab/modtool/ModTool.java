package uk.ac.liv.csc.semanticweblab.modtool;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import uk.ac.liv.csc.semanticweblab.modtool.modularisation.Exclusion;
import uk.ac.liv.csc.semanticweblab.modtool.modularisation.StmtBasedExtraction;
import uk.ac.liv.csc.semanticweblab.modtool.utils.FileUtils;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ModTool {
	public static void main(String args[]) {
		if (args.length == 7) {
			String ontology = null, concept = null, module = null;
			OntModel parentModel, moduleModel;
			int i = 0;
			while (i < args.length) {
				String arg = args[i++];
				if (arg.equals("-ont")) {
					ontology = args[i++];
				} else if (arg.equals("-concept")) {
					concept = args[i++];
				} else if (arg.equals("-module")) {
					module = args[i++];
				}
			}
			parentModel = ModelFactory
					.createOntologyModel(PelletReasonerFactory.THE_SPEC);
			parentModel.read(ontology);
			moduleModel = ModelFactory
					.createOntologyModel(PelletReasonerFactory.THE_SPEC);
			moduleModel.createOntology(concept);
			StmtBasedExtraction sbe = new StmtBasedExtraction(parentModel,
					moduleModel, Exclusion.OWL_DL_EXCLUSION);
			sbe.start(parentModel.getResource(concept));
			FileUtils.outputModule(sbe.getModule(), concept, module, sbe);
		} else {
			System.out.println("ERROR! Command line arguments incorrect.");
		}
	}
}
