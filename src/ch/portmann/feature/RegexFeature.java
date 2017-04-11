package ch.portmann.feature;

import java.util.List;

import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;
import edu.stanford.nlp.util.CoreMap;

public abstract class RegexFeature implements LGTFeature {
	
	//Readme http://nlp.stanford.edu/software/tokensregex.html
	
//	public static String featureType = "featureType";

	public RegexFeature() {

	}

	@Override
	public abstract Sentence run(Sentence sentence);

	public Sentence runRegX(Sentence sentence, String regX) {
		List<List<CoreMap>> matches = sentence.runTRegx(regX);

		for (List<CoreMap> s : matches) {
			sentence.setHitandGroupe(sentence.getStartEndIndex(s));
		}

		return sentence;
	}
	
	/**
	 * This function is used to create .Json file without property group. It replaces the original class Sentence.
	 * This function overrides the run method in the Interface LGTFeature. 
	 */
	@Override
	public abstract Sentence2 run(Sentence2 sentence);
	
	
	/** 
	 * @param sentence
	 * @param featureType: The featureType of the LGTFeature
	 * @param regX: The regular expression
	 */
	public Sentence2 runRegX(Sentence2 sentence, String featureType, String regX) {
		sentence.runTRegx(featureType,regX);

		return sentence;
	}
	
	public String toString(){
		return "RegexFeature";
	}
}
