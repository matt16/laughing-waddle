package ch.portmann.stanford;

import java.util.ArrayList;
import java.util.List;

import ch.portmann.input.TokenLight;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public final class StanfordUtility {

	private StanfordUtility() {


	}

	// UHJO: This is the only point where annotations are created, because it is the only place where we use "pipeline" 
	public static Annotation annotateAll(String originInput) {

		return StanfordCore.pipeline.process(originInput);

	}

	public static List<CoreMap> processSentence(String originInput) {

		Annotation annotation = annotateAll(originInput);

		List<CoreMap> sentences = new ArrayList<CoreMap>();

		for (CoreMap sentenceStanford : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

			sentences.add(sentenceStanford);

		}

		return sentences;

	}

	public static List<TokenLight> tokenizer(CoreMap sentence) {

		List<TokenLight> tokens = new ArrayList<TokenLight>();

		for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

			TokenLight sT = new TokenLight();

			sT.setStemmed(token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase());
			sT.setPos(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));

			tokens.add(sT);
			// tokenPosition++;

		}

		return tokens;
	}

}
