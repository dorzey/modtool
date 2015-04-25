modtool
=
*Automatically exported from code.google.com/p/modtool - __No longer maintained__*

ModTool allows ontology modules to be extracted from an ontology based on a user supplied concept.

ModTool was created by the University of Liverpool's [http://www.csc.liv.ac.uk/~semantic/ Semantic Web Lab]. More information on ModTool can be found [http://www.csc.liv.ac.uk/~pdoran/modtool/ here]

Requirements
-
You will need the following on the classpath to use ModTool:
 * [http://jena.sourceforge.net/ Jena]
 * [http://clarkparsia.com/pellet/ Pellet]

Example
-
```java
	public static void main(String args[]) {
                //The concept you want to extract a module about.
		String concept = "Concept URI"
		parentModel = ModelFactory
				.createOntologyModel(PelletReasonerFactory.THE_SPEC);
                //The ontology the module will be extracted from
		parentModel.read("Ontology URI");
                //The OntologyModel that will contain the module.
		moduleModel = ModelFactory
				.createOntologyModel(PelletReasonerFactory.THE_SPEC);
		moduleModel.createOntology(concept);
                //Instantiate the class that does the extraction.
		StmtBasedExtraction sbe = new StmtBasedExtraction(parentModel,
				moduleModel, Exclusion.OWL_DL_EXCLUSION);
                //Start the extraction.
		sbe.start(parentModel.getResource(concept));
	}
```
