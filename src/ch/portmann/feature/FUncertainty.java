package ch.portmann.feature;

import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;

public class FUncertainty extends RegexFeature implements LGTFeature {

	// SET REGEX TO CREATE A REGEXFEATURE
	String regX = "/vague\\w*|unforecast\\w*|unforeseen|unpredicted|"
			+ "unquantifi\\w*|unreconciled|abeyance\\w*|almost|alteration\\w*|ambigu\\w*|anomal\\w*|"
			+ "anticipat\\w*|apparent\\w*|appear\\w*|approximat\\w*|arbitrar\\w*|assum\\w*|"
			+ "believ\\w*|cautious\\w*|clarification\\w*|conceivabl\\w*|conditional\\w*|"
			+ "confus\\w*|contingen\\w*|could|crossroad\\w*|depend\\w*|destabliz\\w*|"
			+ "deviat\\w*|differ|differed|differing|differs|doubt\\w*|exposure\\w*|fluctuat\\w*|hidden|hinges|"
			+ "imprecis\\w*|improbab\\w*|incompleteness|indefinite\\w*|indetermina\\w*|"
			+ "inexact\\w*|instabilit\\w*|intangible\\w*|likelihood|may|maybe|might|nearly|"
			+ "nonassessable|occasionally|ordinarily|pending|perhaps|possib\\w*|"
			+ "precaution\\w*|predict\\w*|preliminar\\w*|presum\\w*|probab\\w*|random\\w*|"
			+ "reassess\\w*|recalculat\\w*|reconsider\\w*|reexamin\\w*|reinterpret\\w*|"
			+ "revise\\w*|risk\\w*|roughly|rumors|seems|seldom\\w*|sometime\\w*|somewhat|somewhere|"
			+ "speculat\\w*|sporadic\\w*|sudden\\w*|suggest\\w*|susceptibility|tending|"
			+ "tentative\\w*|turbulence|uncertain\\w*|unclear|unconfirmed|undecided|"
			+ "undefined|undesignated|undetectable|undetermin\\w*|undocumented|unexpected\\w*|"
			+ "unfamiliar\\w*|unguaranteed|unhedeged|unidentifi\\w*|unknown\\w*|unobservable|"
			+ "unplanned|unpredict\\w*|unprove\\w*|unseasonabl\\w*|unsettled|unspecifi\\w*|"
			+ "untested|unusual\\w*|unwritten|vagaries|variab\\w*|varian\\w*|variation\\w*|"
			+ "varie\\w*|vary\\w*|volatil\\w*/";
	
	private static final String featureType = "Uncertainty";

	public FUncertainty() {

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

	/*
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
