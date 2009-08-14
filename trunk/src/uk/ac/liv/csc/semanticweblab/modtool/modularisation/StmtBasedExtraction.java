package uk.ac.liv.csc.semanticweblab.modtool.modularisation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uk.ac.liv.csc.semanticweblab.modtool.events.OutputEvent;
import uk.ac.liv.csc.semanticweblab.modtool.events.OutputEventGenerator;
import uk.ac.liv.csc.semanticweblab.modtool.events.OutputEventListener;
import uk.ac.liv.csc.semanticweblab.modtool.utils.ProcessTimer;
import uk.ac.liv.csc.semanticweblab.modtool.utils.RDFUtils;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.OWL;

public class StmtBasedExtraction extends OutputEventGenerator implements
		OutputEventListener {
	private OntModel ontParent, ontModule;
	private Exclusion exclusion;
	// private Set<Property> subjectExclude = CollectionFactory.createSet(
	// RDFS.subClassOf, OWL.disjointWith);
	// private Set<Property> objectInclude = CollectionFactory
	// .createSet(RDFS.subClassOf);
	private Set<Resource> visited = new HashSet<Resource>();
	private List<Resource> toApply = new ArrayList<Resource>();
	private Set<Resource> allSubjectsList;
	private ProcessTimer timer;
	private boolean first = true;
	private boolean includeInternalDisjoints = false;
	private OutputEventListener out;

	public StmtBasedExtraction(OntModel parent, OntModel module,
			Exclusion exclusion) {
		this.ontParent = parent;
		this.ontModule = module;
		this.exclusion = exclusion;
		this.allSubjectsList = clean(this.ontParent.listSubjects().toSet());
	}

	public void setOutputEventListener(OutputEventListener p) {
		this.out = p;
	}

	public void setIncludeInternalDisjoints(boolean value) {
		this.includeInternalDisjoints = value;
	}

	public static Set<Resource> clean(Set<Resource> input) {
		Iterator<Resource> it = input.iterator();
		while (it.hasNext()) {
			if (RDFUtils.isInDefaultNamespace(it.next())) {
				it.remove();
			}
		}
		return input;
	}

	/**
	 * Starts the module extraction process.
	 * 
	 * @param concept
	 *            The resource that the process starts on.
	 */
	public void start(Resource concept) {
		// this.first = false;
		this.toApply.add(concept);
		this.modularise();
		if (this.includeInternalDisjoints) {
			Set<OntClass> res = RDFUtils.namedClassesNotInDN(this.ontModule);
			List<Statement> stats = this.ontParent.listStatements(null,
					OWL.disjointWith, (RDFNode) null).toList();
			for (Statement st : stats) {
				if (res.contains(st.getSubject())
						&& res.contains(st.getObject())) {
					this.ontModule.add(st);
				}
			}
		}
	}

	/**
	 * Starts the module extraction process.
	 * 
	 * @param concept
	 *            The resource that the process starts on.
	 */
	public void startInstrumented(Resource concept) {
		if (this.out != null) {
			this.addOutputEventListener(this.out);
		} else {
			this.addOutputEventListener(this);
		}
		this.outputMessage("Module Extraction Begun");
		this.timer = new ProcessTimer();
		this.timer.setStartTime();
		this.start(concept);
		this.outputMessage("Module Extraction Ended");
	}

	/**
	 * @see uk.ac.liv.csc.semanticweblab.modtool.events.OutputEventListener#eventReceived(uk.ac.liv.csc.semanticweblab.modtool.events.OutputEvent)
	 */
	public void eventReceived(OutputEvent event) {
		System.out.println(event.getMessage());
	}

	private void modularise() {
		for (int i = 0; i < this.toApply.size(); i++) {
			Resource concept = this.toApply.get(i);
			if (!this.visited.contains(concept)) {
				this.outputMessage("RESOURCE = ", concept);
				if (this.allSubjectsList.contains(concept)) {
					this.visited.add(concept);
					this.addTriples(concept);
					// after the first addTriples has been run, first needs to
					// be false
					this.first = false;
				} else {
					this.outputMessage("RESOURCE\t" + concept.toString()
							+ "\tNOT IN LIST");
				}
			}
		}
		// true for the next use
		this.first = true;
	}

	/**
	 * Used in all other iterations of the extraction process.
	 * 
	 * @param it
	 */
	// private void normalIteration(List<Statement> it, Set<Resource> container)
	// {
	// for (Statement st : it) {
	// // If the predicate is not excluded.
	// if (!container.contains(st.getPredicate())) {
	// // Do we need to reverse this statement.
	// if (this.exclusion.toReverseContains(st.getPredicate())) {
	// for (Statement reverseSt : this.ontParent.listStatements(
	// null, st.getPredicate(), st.getSubject()).toList()) {
	// this.ontModule.add(reverseSt);
	// this.outputMessage(reverseSt.toString(),
	// "REVERSE ADDED");
	// if (reverseSt.getObject().isResource()
	// && !this.exclusion.toReverseContains(reverseSt
	// .getObject())
	// && !this.visited.contains(reverseSt
	// .getResource())
	// && this.allSubjectsList.contains(reverseSt
	// .getResource())
	// && !RDFUtils.isInDefaultNamespace(reverseSt
	// .getResource())) {
	// this.toApply.add(reverseSt.getResource());
	// }
	// }
	// } else {
	// this.ontModule.add(st);
	// this.outputMessage(st.toString(), "ADDED");
	// // If the object is not excluded.
	// if (!container.contains(st.getObject())) {
	// if (st.getObject().isResource()
	// && !this.visited.contains(st.getResource())
	// && this.allSubjectsList.contains(st
	// .getResource())
	// && !RDFUtils.isInDefaultNamespace(st
	// .getResource())) {
	// this.toApply.add(st.getResource());
	// }
	// }
	// }
	// }
	// }
	// }
	// private void addTriples(Resource concept) {
	// // check predicates of each to see if they are permissable.
	// StmtIterator subjectIterator = this.ontParent
	// .listStatements(new SimpleSelector(concept, null,
	// (Resource) null));
	// this.outputMessage("SUBJECTS");
	// while (subjectIterator.hasNext()) {
	// Statement st = subjectIterator.nextStatement();
	// boolean toAdd = false;
	// if (this.first) {
	// toAdd = !this.exclusion.firstIterationExcludeContains(st
	// .getPredicate());
	// } else {
	// toAdd = !this.exclusion.toExcludeContains(st.getPredicate());
	// }
	// if (toAdd) {
	// this.outputMessage(st);
	// this.ontModule.add(st);
	// if (st.getObject().isResource()) {
	// if (!RDFUtils.isInDefaultNamespace(st.getObject())) {
	// Resource res = st.getResource();
	// this.outputMessage("TOAPPLY ADD = ", res);
	// this.toApply.add(res);
	// }
	// }
	// }
	// }
	// this.outputMessage("OBJECTS");
	// StmtIterator objectIterator = this.ontParent
	// .listStatements(new SimpleSelector(null, null, concept));
	// while (objectIterator.hasNext()) {
	// Statement st = objectIterator.nextStatement();
	// if (this.objectInclude.contains(st.getPredicate().toString())) {
	// if (st.getSubject().isAnon()) {
	// this.outputMessage("DON'T ADD = ", st);
	// } else {
	// if (!RDFUtils.isInDefaultNamespace(st.getSubject())) {
	// this.outputMessage(st);
	// this.ontModule.add(st);
	// this.toApply.add(st.getSubject());
	// }
	// }
	// }
	// }
	// }
	private void addTriples(Resource concept) {
		// check predicates of each to see if they are permissable.
		List<Statement> subjects = this.ontParent.listStatements(concept, null,
				(RDFNode) null).toList();
		this.outputMessage("SUBJECTS");
		for (Statement st : subjects) {
			boolean toAdd = false;
			if (this.first) {
				toAdd = !this.exclusion.firstIterationExcludeContains(st
						.getPredicate());
			} else {
				toAdd = !this.exclusion.toExcludeContains(st.getPredicate());
			}
			if (toAdd) {
				this.outputMessage(st);
				this.ontModule.add(st);
				if (st.getObject().isResource()
						&& !RDFUtils.isInDefaultNamespace(st.getObject())
						&& !this.visited.contains(st.getResource())) {
					this.outputMessage("TOAPPLY ADD = ", st.getResource());
					this.toApply.add(st.getResource());
				}
			}
		}
		this.outputMessage("OBJECTS");
		List<Statement> objects = this.ontParent.listStatements(null, null,
				concept).toList();
		for (Statement st : objects) {
			if (this.exclusion.toIncludeObjectContains(st.getPredicate())
			// && !st.getSubject().isAnon()
					&& !RDFUtils.isInDefaultNamespace(st.getSubject())) {
				// XXX Asymmetry: a statement gets added only if the subject is
				// not blank. That's not true for the subjects. Is it correct?
				this.ontModule.add(st);
				this.outputMessage("OBJECT ", st);
				if (!this.visited.contains(st.getSubject())
						&& !RDFUtils.isInDefaultNamespace(st.getSubject())) {
					this.outputMessage("TOAPPLY ADD = ", st.getSubject());
					this.toApply.add(st.getSubject());
				}
			}
			// else {
			// this.outputMessage("DON'T ADD = ", st);
			// }
		}
	}

	public OntModel getModule() {
		return this.ontModule;
	}
}
