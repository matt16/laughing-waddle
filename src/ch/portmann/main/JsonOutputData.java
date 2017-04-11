package ch.portmann.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;


/**
 * This class is used to write the class Sentence/Sentence2 into .Json file.
 */

public class JsonOutputData {

	private int sentenceIndex;
	List<String> tokens = new ArrayList<String>();
	private Integer[] groups;
	private Map<String, boolean[]> isHit;
	private String orginalSentence;


//	public JsonOutputData(Sentence sentence) {
//
//		this.groups = sentence.getGroupes();
//		this.isHit = sentence.getIsHit();
//		this.orginalSentence = sentence.getOrginalSentence();
//
//		for (CoreLabel cl : sentence.getTokens()) {
//
//			this.tokens.add(cl.get(CoreAnnotations.TextAnnotation.class));
//
//		}
//	}
	
	public JsonOutputData(Sentence2 sentence) {
		
		this.sentenceIndex = sentence.getSentenceIndex();
		this.isHit = sentence.getIsHit();
		this.orginalSentence = sentence.getOrginalSentence();

		for (CoreLabel cl : sentence.getTokens()) {

			this.tokens.add(cl.get(CoreAnnotations.TextAnnotation.class));

		}
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public Integer[] getGroupes() {
		return groups;
	}

	public void setGroupes(Integer[] groupes) {
		this.groups = groupes;
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
	
	
}
