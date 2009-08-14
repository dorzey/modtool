package uk.ac.liv.csc.semanticweblab.modtool.modularisation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Provides Exclusion constants that can be passed to
 * {@link StmtBasedExtraction}.
 * 
 * @author pdoran
 */
public final class Exclusion {
	// OWL DL
	private static final Resource[] firstExcludeDL = new Resource[] {
			OWL.disjointWith, RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toExcludeDL = new Resource[] {
			OWL.disjointWith, RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toReverseDL = new Resource[] {
			RDFS.subClassOf, RDFS.label, RDFS.comment };
	// OWL LITE
	private static final Resource[] firstExcludeLITE = new Resource[] {
			RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toExcludeLITE = new Resource[] {
			RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toReverseLITE = new Resource[] { RDFS.subClassOf };
	// RDFS
	private static final Resource[] firstItExcludeRDFS = new Resource[] {
			RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toExcludeRDFS = new Resource[] {
			RDFS.subClassOf, RDFS.label, RDFS.comment };
	private static final Resource[] toReverseRDFS = new Resource[] { RDFS.subClassOf };
	private static final Resource[] toIncludeObject = new Resource[] { RDFS.subClassOf };
	/**
	 * OWL DL exclusions.
	 */
	public static final Exclusion OWL_DL_EXCLUSION = new Exclusion(toExcludeDL,
			firstExcludeDL, toReverseDL, toIncludeObject);
	/**
	 * OWL Lite exclusions.
	 */
	public static final Exclusion OWL_LITE_EXCLUSION = new Exclusion(
			toExcludeLITE, firstExcludeLITE, toReverseLITE, toIncludeObject);
	/**
	 * RDFS exclusions.
	 */
	public static final Exclusion RDFS_EXCLUSION = new Exclusion(toExcludeRDFS,
			firstItExcludeRDFS, toReverseRDFS, toIncludeObject);
	private final Set<Resource> toExclude;
	private final Set<Resource> objectToInclude;
	private final Set<Resource> firstItExclude;
	private final Set<Resource> toReverse;

	private Exclusion(Resource[] toExclude, Resource[] firstIterationExclude,
			Resource[] toReverse, Resource[] toInclude) {
		this.toExclude = new HashSet<Resource>(Arrays.asList(toExclude));
		this.firstItExclude = new HashSet<Resource>(Arrays.asList(firstIterationExclude));
		this.toReverse = new HashSet<Resource>(Arrays.asList(toReverse));
		this.objectToInclude = new HashSet<Resource>(Arrays.asList(toInclude));
	}

	public void followDisjoints() {
		this.toExclude.remove(OWL.disjointWith);
	}

	public Set<Resource> getToExclude() {
		return this.toExclude;
	}

	public Set<Resource> getFirstToExclude() {
		return this.firstItExclude;
	}

	public Set<Resource> getToReverse() {
		return this.toReverse;
	}

	public boolean toReverseContains(RDFNode r) {
		return this.toReverse.contains(r);
	}

	public boolean firstIterationExcludeContains(RDFNode r) {
		return this.firstItExclude.contains(r);
	}

	public boolean toExcludeContains(RDFNode r) {
		return this.toExclude.contains(r);
	}

	public boolean toIncludeObjectContains(RDFNode n) {
		return this.objectToInclude.contains(n);
	}
}
