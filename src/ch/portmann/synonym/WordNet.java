package ch.portmann.synonym;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.portmann.input.TokenLight;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class WordNet implements Synonym, Serializable {

	private IDictionary dict;

	public WordNet() throws IOException {
		dict = new Dictionary(new URL("file", null, "files/wordnet/dict"));
		dict.open();
	}

	public POS stanfordToWordnetPOS(String stanforPOS) {

		if (stanforPOS.equals("JJ") || stanforPOS.equals("JJR") || stanforPOS.equals("JJS"))
			return POS.ADJECTIVE;

		else if (stanforPOS.equals("RB") || stanforPOS.equals("RBR") || stanforPOS.equals("RBS"))
			return POS.ADVERB;

		else if (stanforPOS.equals("NN") || stanforPOS.equals("NNS") || stanforPOS.equals("NNP")
				|| stanforPOS.equals("NNPS"))
			return POS.NOUN;

		else if (stanforPOS.equals("VB") || stanforPOS.equals("VBD") || stanforPOS.equals("VBG")
				|| stanforPOS.equals("VBN") || stanforPOS.equals("VBP") || stanforPOS.equals("VBZ"))
			return POS.VERB;

		else
			return null;
	}

	/***
	 * This method returns the number of meanings.
	 * 
	 * @param wordLookup
	 * @param wordArt
	 * @return
	 */
	public int getMeaningsSize(TokenLight token) {

		if (stanfordToWordnetPOS(token.getPos()) != null) { // just adjective,
															// adverbs, nouns,
															// and verbs are
															// expanded
			IIndexWord idxWord;
			int size = 0;
			try {

				idxWord = dict.getIndexWord(token.getStemmed(), POS.ADJECTIVE);
				if (idxWord != null)
					size = idxWord.getWordIDs().size();

				idxWord = dict.getIndexWord(token.getStemmed(), POS.NOUN);
				if (idxWord != null)
					size = size + idxWord.getWordIDs().size();

				idxWord = dict.getIndexWord(token.getStemmed(), POS.VERB);
				if (idxWord != null)
					size = size + idxWord.getWordIDs().size();

				idxWord = dict.getIndexWord(token.getStemmed(), POS.ADVERB);
				if (idxWord != null)
					size = size + idxWord.getWordIDs().size();
			} catch (Exception e) {
				return 0;
			}

			return size;
		}

		return 0;
	}

	public Set<List<TokenLight>> getSynos(String token, int meaningID) {

		Set<List<TokenLight>> synoWords = new HashSet<List<TokenLight>>();

		TokenLight t = new TokenLight();

		t.setStemmed(token);
		t.setPos("NN");
		synoWords.addAll(getSynos(t, meaningID));

		t.setPos("JJ");
		synoWords.addAll(getSynos(t, meaningID));

		t.setPos("VB");
		synoWords.addAll(getSynos(t, meaningID));

		t.setPos("RB");
		synoWords.addAll(getSynos(t, meaningID));

		return synoWords;

	}

	/***
	 * This method looks for synonyms. Only first order synonyms are allowed.
	 * 
	 * @param wordLookupSy
	 * @param wordArt
	 * @param meaningID
	 * @return
	 */
	public Set<List<TokenLight>> getSynos(TokenLight token, int meaningID) {

		Set<List<TokenLight>> synoWords = new HashSet<List<TokenLight>>();

		// initialize
		List<TokenLight> synoToken = new ArrayList<TokenLight>();
		TokenLight t = new TokenLight();

		IWordID wordID;

		// look up first sense of the word
		// delete article
		IIndexWord idxWord = dict.getIndexWord(token.getStemmed(), stanfordToWordnetPOS(token.getPos()));
		try {
			wordID = idxWord.getWordIDs().get(meaningID); // meaning
		} catch (Exception e) {

			synoWords.clear();
			return synoWords;
		}
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();

		// iterate over words associated with the synset
		for (IWord w : synset.getWords()) {

			// don't consider look up string
			if (!(token.getStemmed().replaceAll(" ", "").toLowerCase()
					.equals(w.getLemma().replaceAll("_", "").toLowerCase()))) {
				synoToken = new ArrayList<TokenLight>();
				for (String s : w.getLemma().split("_")) {

					t = new TokenLight();
					t.setStemmed(s.toLowerCase());
					t.setPos(token.getPos());

					synoToken.add(t);
				}
				synoWords.add(synoToken);

				// TODO: Make it nice!!
				// same with articles
				// THE
				synoToken = new ArrayList<TokenLight>();
				t = new TokenLight();
				t.setStemmed("the");
				t.setPos(token.getPos());
				synoToken.add(t);

				for (String s : w.getLemma().split("_")) {
					t = new TokenLight();
					t.setStemmed(s.toLowerCase());
					t.setPos(token.getPos());
					synoToken.add(t);
				}
				synoWords.add(synoToken);

				// A
				synoToken = new ArrayList<TokenLight>();
				t = new TokenLight();
				t.setStemmed("a");
				t.setPos(token.getPos());
				synoToken.add(t);

				for (String s : w.getLemma().split("_")) {
					t = new TokenLight();
					t.setStemmed(s.toLowerCase());
					t.setPos(token.getPos());
					synoToken.add(t);
				}
				synoWords.add(synoToken);

				// AN
				synoToken = new ArrayList<TokenLight>();
				t = new TokenLight();
				t.setStemmed("an");
				t.setPos(token.getPos());
				synoToken.add(t);

				for (String s : w.getLemma().split("_")) {
					t = new TokenLight();
					t.setStemmed(s.toLowerCase());
					t.setPos(token.getPos());
					synoToken.add(t);
				}
				synoWords.add(synoToken);

			}
		}
		return synoWords;
	}

	/***
	 * This methods returns all synonyms
	 * 
	 * @param wordLookupSy
	 * @param wordArt
	 * @return
	 */
	public Set<List<TokenLight>> getSynos(TokenLight token) {

		token.setStemmed(
				token.getStemmed().toLowerCase().replace("the ", "").replaceAll("a ", "").replaceAll("an ", ""));

		Set<List<TokenLight>> synoWords = new HashSet<List<TokenLight>>();

		// set first token
		List<TokenLight> synoToken = new ArrayList<TokenLight>();
		for (String s : token.getStemmed().split(" ")) {

			TokenLight t = new TokenLight();
			t.setStemmed(s.toLowerCase());
			t.setPos(token.getPos());

			if (!t.getStemmed().equals(""))
				synoToken.add(t);

		}
		synoWords.add(synoToken);

		// handle here articles next -> the following

		for (int i = 0; i < getMeaningsSize(token); i++) {

			synoWords.addAll(getSynos(token.getStemmed(), i));

		}

		// add articles to all

		return synoWords;

	}
}
