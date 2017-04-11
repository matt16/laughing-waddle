package ch.portmann.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapdb.Serializer;

import ac.biu.nlp.normalization.BiuNormalizer;
import ch.portmann.cache.MapDBStore;
import ch.portmann.stanford.StanfordUtility;
import edu.stanford.nlp.util.CoreMap;


/**
 * This class is used to create .Json file without property "group". 
 * It replaces the original class "Text".
 */


public class Text2 {

	List<Sentence2> sentences = new ArrayList<Sentence2>();

	@SuppressWarnings("unchecked")
	public Text2(String inputText) throws Exception {

		List<CoreMap> inputTextStanford = new ArrayList<CoreMap>();

		// Check DB if we have this text in the DB
		MapDBStore mDB = MapDBStore.getInstance();
		Map<String, byte[]> stanfordMap = mDB.getDb()
				.hashMap("inputText_A1912", Serializer.STRING, Serializer.BYTE_ARRAY).createOrOpen();

		if (stanfordMap.get(inputText) != null) {
			inputTextStanford.addAll((List<CoreMap>) mDB.deserialize(stanfordMap.get(inputText)));
		} else {

			// Number normalization and replace tokens
			File stringRulesFile = new File("files/stringRules/string_rules.txt");

			BiuNormalizer n = new BiuNormalizer(stringRulesFile);
			inputText = n.normalize(inputText);

			// Stanford parsing
			// UHJO: this is where we trigger Annotations
			inputTextStanford = StanfordUtility.processSentence(inputText);

			// store sentence to db
			stanfordMap.put(inputText, mDB.serialize(inputTextStanford));
		}

		// loop over stanford output
		
		for (int i = 0; i < inputTextStanford.size(); i++){
			CoreMap sentence = inputTextStanford.get(i);
			Sentence2 s = new Sentence2(sentence);
			s.setSentenceIndex(i);
			sentences.add(s);
		}
	}

	public List<Sentence2> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence2> sentences) {
		this.sentences = sentences;
	}

}
