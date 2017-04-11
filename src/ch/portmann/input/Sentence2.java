package ch.portmann.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.portmann.stanford.TenseAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.Env;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.util.CoreMap;

/*
 * This class is used to create .Json file without property "group". 
 * It replaces the original class "Sentence".
 */

public class Sentence2 implements java.io.Serializable {
	
	private static final long serialVersionUID = 1477333605667795092L;
	private String orginalSentence;
	private CoreMap stanfordSentence;
	private Map<String, boolean[]> isHit;
	private int sentenceIndex;

	public Sentence2(CoreMap stanfordSentence) throws IOException, ClassNotFoundException {

		this.stanfordSentence = stanfordSentence;
		this.orginalSentence = this.stanfordSentence.get(CoreAnnotations.TextAnnotation.class);

		// Initialize isEqual
		this.isHit = new HashMap<>();

//		boolean[] temp = new boolean[this.getSize()];
//		for (int i = 0; i <= this.getSize() - 1; i++) {
//			temp[i] = false;
//		}
		
		
	}
	
	public void runTRegx(String featureType, String tRegx) {

		// UHJO: This functions gets a regular expression (defined by us), "parses" it (using compile(tRegx)), and "unpacks" the result
		// UHJO: Here we need to add the "global" env variable, that contains the info what "tense" actually means!
		// Example: 
		Env env = TokenSequencePattern.getNewEnv();
		env.bind("tense", TenseAnnotation.class);
		TokenSequencePattern pattern = TokenSequencePattern.compile(env, tRegx);
		
		// This makes no sense like this, because we "define" the same thing in two places, but for the time being, this is the only way.
		// TokenSequencePattern pattern = TokenSequencePattern.compile(tRegx);
	
		List<CoreLabel> tokens = this.getTokens();

		TokenSequenceMatcher matcher = pattern.getMatcher(tokens);
		boolean[] hitVector = new boolean[this.getSize()];

		while (matcher.find()) {
			for(int i = matcher.start(); i < matcher.end(); i++){
				hitVector[i] = true;
			}
		}
		isHit.put(featureType, hitVector);
	}
	
	
	public CoreMap getStanfordSentence() {
		return stanfordSentence;
	}
	
	public int getSize() {
		return this.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class).size();
	}

	public List<CoreLabel> getTokens() {
		return this.stanfordSentence.get(CoreAnnotations.TokensAnnotation.class);
	}

	public Map<String, boolean[]> getIsHit() {
		return isHit;
	}

	public String getOrginalSentence() {
		return orginalSentence;
	}

	public void setOrginalSentence(String orginalSentence) {
		this.orginalSentence = orginalSentence;
	}
	
	public void setSentenceIndex(int sentenceIndex){
		this.sentenceIndex = sentenceIndex;
	}
	public int getSentenceIndex(){
		return this.sentenceIndex;
	}
}
