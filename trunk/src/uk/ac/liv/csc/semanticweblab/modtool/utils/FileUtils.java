package uk.ac.liv.csc.semanticweblab.modtool.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import uk.ac.liv.csc.semanticweblab.modtool.events.OutputEventGenerator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class FileUtils {
	/**
	 * @param startConcept
	 *            Used to give module an appropriate name.
	 * @param m
	 *            the module
	 * @param fileLocation
	 *            base directory for the file
	 * @param log
	 *            the log component
	 */
	public static void outputModule(Model m, String startConcept,
			String fileLocation, OutputEventGenerator log) {
		String filename = fileLocation;
		log.outputMessage("FILENAME = " + filename);
		try {
			OutputStream out = new FileOutputStream(filename);
			m.write(out, "RDF/XML-ABBREV");
			out.close();
		} catch (FileNotFoundException e) {
			log.outputMessage("File: " + filename + " not found.");
		} catch (IOException i) {
			log.outputMessage("IO error. Please check connections.");
		}
		log.outputMessage("Module written to file.");
	}

	public static void printAll(StmtIterator st, OutputEventGenerator log) {
		while (st.hasNext()) {
			Statement element = st.next();
			log.outputMessage(element);
		}
	}
}
