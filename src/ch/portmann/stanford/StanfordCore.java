package ch.portmann.stanford;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

// A wrapper for making our life easier: we do not want to write the initilization code over and over again
public class StanfordCore {

	// This is the only "pipeline" we will ever use, and we only use it through the class StanfordCore (it's public!)
	public static StanfordCoreNLP pipeline;

	public static void init() {
		Properties props = new Properties();

		// UHJO: What does this do? Do we extend Stanford's framework by attaching something that we coded? 
		// Is this what is used when we add "tense" to the "annotators" property
		//
		// Here we try to add the custom annotator to the "pipeline" (think: job-list) by adding its name as a string!
		props.setProperty("customAnnotatorClass.tense", "ch.portmann.stanford.TenseAnnotator");
		
		// props.setProperty("customAnnotatorClass.tense",
		// "ch.lgt.ming.corenlp.TenseAnnotator");
		// props.setProperty("annotators", "tokenize, ssplit");
		// props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,
		// parse");
		 //props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, tense");

		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, tense");

		// here we set up the Stanford NLP machine
		pipeline = new StanfordCoreNLP(props);
	}
}
