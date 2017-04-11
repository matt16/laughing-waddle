package ch.portmann.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.util.CoreMap;

public class Test {
	public static void main(String[] args) throws Exception {

//		String input = "This is the hundred same.";
//		String solution = "This is the 100 same.";
//
//		ProcessCompare pc = new ProcessCompare(input, solution);
//
//		pc.process();
//		
//		System.out.println(new File("WebContent").toPath().toRealPath().toString());
		
		

	}

	// Artikel noch machen
	// Mutationen berücksichtigen
	// Konijunktionen tauschen

	public static boolean process(String inputText) throws ClassNotFoundException, IOException {

		StanfordCore.init();
		Sentence sentence = new Sentence(null);

		System.out.println(sentence.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class).size());

		for (CoreLabel token : sentence.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class)) {
			System.out.print(" ; ");
			System.out.print(token.get(CoreAnnotations.IndexAnnotation.class));
			System.out.print(" ; ");
			System.out.print(token.get(CoreAnnotations.TextAnnotation.class));
			System.out.print(" ; ");
			System.out.println(token.get(CoreAnnotations.OriginalTextAnnotation.class));

		}

		String reg = "([{word:/\\\\?/}] [{word:/-LSB-?/}]) []* ([{word:/\\\\?/}] [{word:/-RSB-?/}])";
		// String reg = "[{word:/\\\\?/}] [{word:/-LSB-?/}] []* [{word:/\\\\?/}]
		// [{word:/-RSB-?/}]";
		TokenSequencePattern pattern = TokenSequencePattern.compile(reg);

		List<CoreLabel> tokens = sentence.getTokens();

		TokenSequenceMatcher matcher = pattern.getMatcher(tokens);

		while (matcher.find()) {
			List<CoreMap> matchedTokens = matcher.groupNodes();

			System.out.println("!!!!!!");
			System.out.print(matchedTokens.toString());
			System.out.println(" ; ");
			System.out.println("StartIndex:");
			System.out.println(matchedTokens.get(0).get(CoreAnnotations.IndexAnnotation.class));
			System.out.println("StartIndex:");
			System.out.println(matchedTokens.get(matchedTokens.size() - 1).get(CoreAnnotations.IndexAnnotation.class));

		}

		return false;
	}
}