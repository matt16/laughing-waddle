package ch.portmann.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.portmann.stanford.TenseAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.ling.tokensregex.Env;
import edu.stanford.nlp.util.CoreMap;

// UHJO: sentence is a wrapper for CoreMap plus more things
public class Sentence implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1477333605667795092L;
	private String orginalSentence;
	private CoreMap stanfordSentence;
	private Integer[] groups;
	protected boolean[] isHit;

	public Sentence(CoreMap stanfordSentence) throws IOException, ClassNotFoundException {

		this.stanfordSentence = stanfordSentence;
		this.orginalSentence = this.stanfordSentence.get(CoreAnnotations.TextAnnotation.class);

		// Initialize groups
		this.groups = new Integer[getSize()];

		// Initialize isEqual
		this.isHit = new boolean[getSize()];

		for (int i = 0; i <= this.getSize() - 1; i++) {
			this.groups[i] = i;
			this.isHit[i] = false;
		}

	}

	public boolean setHitandGroupe(Integer[] startEndPos) {

		if (markHit(startEndPos) && makrGroupe(startEndPos)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean markHit(Integer[] startEndPos) {

		if (isUngrouped(startEndPos)) {
			if (startEndPos[1] < this.getGroupes().length) {
				for (int i = startEndPos.clone()[0]; i <= startEndPos[1]; i++) {

					isHit[i] = true;
				}

			} else {
				return false;
			}
		}
		this.setIsHit(isHit);
		return true;
	}

	public boolean makrGroupe(Integer[] startEndPos) {

		if (isUngrouped(startEndPos)) {

			for (int i = startEndPos.clone()[0]; i <= startEndPos[1]; i++) {

				if (startEndPos[0] - 1 >= 0)
					this.getGroupes()[i] = this.getGroupes()[startEndPos[0] - 1] + 1;
				else
					this.getGroupes()[i] = 0;

			}

			if (startEndPos[1] + 1 < this.getGroupes().length) {

				// recursive to make it with all following groups
				makrGroupe(getGroupRange(startEndPos[1] + 1));

			}
			return true;
		} else {
			return false;
		}

	}

	public int getNumOfGroupes() {

		return this.getGroupes()[this.getGroupes().length - 1];

	}

	public boolean isUngrouped(Integer[] startEndPos_O) {

		Integer[] startEndPos = startEndPos_O.clone();

		// if just one token
		if ((startEndPos[0] - startEndPos[1]) == 0)
			if (this.getIsHit()[startEndPos[0]])
				return false;

		// corner cases
		if (startEndPos[0] != 0)
			startEndPos[0] = startEndPos[0] - 1;

		if (startEndPos[1] == this.getGroupes().length - 1)
			startEndPos[1] = startEndPos[1] - 1;

		if (startEndPos[1] > this.getGroupes().length - 1)
			return false;

		for (int i = 0; i <= startEndPos[1] - startEndPos[0]; i++) {
			// range is one longer than startEndPos
			if (this.getGroupes()[startEndPos[0] + i] == this.getGroupes()[startEndPos[0] + i + 1])
				return false;
		}

		return true;
	}

	public List<List<CoreMap>> runTRegx(String tRegx) {

		// UHJO: This functions gets a regular expression (defined by us), "parses" it (using compile(tRegx)), and "unpacks" the result
		// UHJO: Here we need to add the "global" env variable, that contains the info what "tense" actually means!
		// Example: 
		Env env = TokenSequencePattern.getNewEnv();
		env.bind("tense", TenseAnnotation.class);
		TokenSequencePattern pattern = TokenSequencePattern.compile(env, tRegx);
		//
		// This makes no sense like this, because we "define" the same thing in two places, but for the time being, this is the only way.
//		TokenSequencePattern pattern = TokenSequencePattern.compile(tRegx);
	
		List<CoreLabel> tokens = this.getTokens();

		TokenSequenceMatcher matcher = pattern.getMatcher(tokens);

		List<List<CoreMap>> matchedTokens = new ArrayList<List<CoreMap>>();
		while (matcher.find()) {
			List<CoreMap> mT = matcher.groupNodes();
			matchedTokens.add(mT);
		}

		return matchedTokens;
	}

	public List<Integer[]> findbetween(String regX1, String regX2) {

		List<Integer[]> returnPositions = new ArrayList<Integer[]>();

		List<List<CoreMap>> tRegX1 = runTRegx(regX1);
		List<List<CoreMap>> tRegX2 = runTRegx(regX2);

		for (int i = 0; i < tRegX1.size(); i++) {

			Integer[] positions = new Integer[2];

			if (tRegX2.size() >= i) {

				// array position and not token index!!!!!
				positions[0] = getStartEndIndex(tRegX1.get(i))[1] + 1;
				positions[1] = getStartEndIndex(tRegX2.get(i))[0] - 1;

				returnPositions.add(positions);
			}

		}

		return returnPositions;
	}

	public String getString(int startPos, int endPos) {

		String returnString = "";
		for (int i = startPos; i <= endPos; i++) {

			returnString = returnString + getToken(i).get(CoreAnnotations.TextAnnotation.class) + " ";

		}

		// replace brackets
		returnString = returnString.replace("-LSB-", "[");
		returnString = returnString.replace("-RSB-", "]");
		returnString = returnString.replace("-LCB-", "{");
		returnString = returnString.replace("-RCB-", "}");
		returnString = returnString.replace("-LRB-", "(");
		returnString = returnString.replace("-RRB-", ")");

		return returnString;
	}

	public TokenLight getLemmaNgram(int startPos, int endPos) {

		return getNgram(startPos, endPos, true);

	}

	public TokenLight getOrginalNgram(int startPos, int endPos) {

		return getNgram(startPos, endPos, false);

	}

	public TokenLight getNgram(int startPos, int endPos, boolean lemmaFlag) {

		String lemmaString = "";
		String orginalString = "";
		String posString = "";
		TokenLight token = new TokenLight();

		// check if pos is adj, adv, nn or vb
		List<String> posCom = Arrays.asList("JJ", "JJR", "JJS", "RB", "RBR", "RBS", "NN", "NNS", "NNP", "NNPS", "VB",
				"VBD", "VBG", "VBN", "VBP", "VBZ");

		if (endPos <= this.getTokens().size()) {
			for (int i = startPos; i <= endPos; i++) {

				if (i != startPos)
					lemmaString = lemmaString + " ";
				lemmaString = lemmaString + getToken(i).get(CoreAnnotations.LemmaAnnotation.class);
				orginalString = orginalString + " ";
				orginalString = orginalString + getToken(i).get(CoreAnnotations.TextAnnotation.class);

				if (posCom.contains(getToken(i).get(CoreAnnotations.PartOfSpeechAnnotation.class))) {

					posString = getToken(i).get(CoreAnnotations.PartOfSpeechAnnotation.class);

				}
			}

			if (lemmaFlag) {
				token.setStemmed(lemmaString);
				token.setPos(posString);
			} else {
				token.setStemmed(orginalString);
				token.setPos(posString);
			}
		}
		return token;
	}

	public Integer[] convertSentencePosToStartEnd(Integer[] sentencePos) {

		Integer[] returnPositions = new Integer[2];

		int index = 0;
		for (CoreLabel cl : this.getTokens()) {

			if (cl.endPosition() > sentencePos[0]) {
				returnPositions[0] = index;
				break;
			}
			index++;

		}

		index = 0;
		for (CoreLabel cl : this.getTokens()) {

			if (cl.beginPosition() >= sentencePos[1]) {
				returnPositions[1] = index - 1;
				break;

			}
			index++;
		}

		// if it is last token
		if (sentencePos[1] == this.getOrginalSentence().length())
			returnPositions[1] = this.getTokens().size() - 1;

		return returnPositions;

	}

	// Array Index!!!
	public Integer[] getStartEndIndex(List<CoreMap> tokens) {

		Integer[] returnPositions = new Integer[2];

		returnPositions[0] = tokens.get(0).get(CoreAnnotations.IndexAnnotation.class) - 1;
		returnPositions[1] = tokens.get(tokens.size() - 1).get(CoreAnnotations.IndexAnnotation.class) - 1;

		return returnPositions;

	}

	public Integer[] getGroupRangeByGNum(int groupeId) {

		for (int i = 0; i < this.getGroupes().length; i++) {
			if (this.getGroupes()[i] == groupeId) {
				return getGroupRange(i);
			}
		}
		return null;
	}

	public boolean hasHit() {
		for (int i = 0; i < this.getIsHit().length; i++) {
			if (this.getIsHit()[i] == true) {
				return true;
			}
		}
		return false;
	}

	public boolean isAllHit() {
		for (int i = 0; i < this.getIsHit().length; i++) {
			if (this.getIsHit()[i] == false) {
				return false;
			}
		}
		return true;
	}

	public Integer[] getGroupRange(int initialPoint) {

		Integer[] returnPositions = new Integer[2];

		int groupNumber = this.getGroupes()[initialPoint];

		// starting point (go back)
		int startPosition = initialPoint;
		for (int i = initialPoint; i >= 0; i--) {
			if (this.getGroupes()[i] == groupNumber)
				startPosition = i;
			else
				break;
		}

		// end point
		int endPosition = initialPoint;
		// starting point (go back)
		for (int i = initialPoint; i < this.getGroupes().length; i++) {
			if (this.getGroupes()[i] == groupNumber)
				endPosition = i;
			else
				break;
		}

		returnPositions[0] = startPosition;
		returnPositions[1] = endPosition;

		return returnPositions;
	}

	public CoreMap getStanfordSentence() {
		return stanfordSentence;
	}

	public int getSize() {
		return this.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class).size();
	}

	public CoreLabel getToken(int index) {

		return getTokens().get(index);

	}

	public List<CoreLabel> getTokens() {

		return this.stanfordSentence.get(CoreAnnotations.TokensAnnotation.class);
	}

	public List<String> getLemmas() {

		List<String> lemmas = new ArrayList<String>();
		for (CoreLabel t : this.stanfordSentence.get(CoreAnnotations.TokensAnnotation.class)) {
			lemmas.add(t.get(CoreAnnotations.LemmaAnnotation.class));

		}
		return lemmas;

	}

	public List<String> getOrginalString() {

		List<String> orginalString = new ArrayList<String>();
		for (CoreLabel t : this.stanfordSentence.get(CoreAnnotations.TokensAnnotation.class)) {
			orginalString.add(t.get(CoreAnnotations.TextAnnotation.class));

		}
		return orginalString;

	}

	public void setStanfordSentence(CoreMap stanfordSentence) {
		this.stanfordSentence = stanfordSentence;
	}

	public String getOriginSentence() {

		return this.stanfordSentence.get(CoreAnnotations.TextAnnotation.class);

	}

	public Integer[] getGroupes() {
		return groups;
	}

	public void setGroupes(Integer[] groupes) {
		this.groups = groupes;
	}

	public boolean[] getIsHit() {
		return isHit;
	}

	public void setIsHit(boolean[] isEqual) {
		this.isHit = isEqual;
	}

	public String getOrginalSentence() {
		return orginalSentence;
	}

	public void setOrginalSentence(String orginalSentence) {
		this.orginalSentence = orginalSentence;
	}
	

}
