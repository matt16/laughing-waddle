package ch.portmann.feature;

import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;

public class FConditionality2 extends RegexFeature implements LGTFeature {

	// SET REGEX TO CREATE A REGEXFEATURE
	String regX = "([{word:/[Ii]f/}]&[tense:Future])[]*[{word:/(then)|,/}]?";

	private static final String featureType = "Conditionality2";

	public FConditionality2() {

	}

	@Override
	public Sentence run(Sentence sentence) {

		sentence = this.runRegX(sentence, this.getRegX());
		return sentence;

	}

	public String getRegX() {
		return regX;
	}

	public void setRegX(String regX) {
		this.regX = regX;
	}
	
	/**
	 * This function is used to create .Json file without property group. 
	 * It replaces the original class Sentence
	 */
	
	@Override
	public Sentence2 run(Sentence2 sentence) {
		sentence = this.runRegX(sentence, featureType, this.getRegX());
		return sentence;
	}

	public String toString() {
		return featureType;
	}

}
