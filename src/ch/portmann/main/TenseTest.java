package ch.portmann.main;
import java.util.List;

import ch.portmann.stanford.StanfordCore;
import ch.portmann.stanford.TenseRule;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

public class TenseTest {
	public static void main(String[] args){
		
		StanfordCore.init();
		
//		String s = "I am so surprised about the news. The amazing IPO change that maz restart the flow tof the news stocks.";
		String s = "If the rate had been increased the result would have been different.";
				
		Annotation document = StanfordCore.pipeline.process(s);
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		List<CoreLabel> tokens = sentences.get(0).get(CoreAnnotations.TokensAnnotation.class);
		System.out.println(sentences);
//		for(int i = 0; i <tokens.size(); i++){
//			System.out.println(tokens.get(i).toShorterString());
//		}
		
		for (int i = 0; i <sentences.size(); i++){
			TenseRule tenseRule = new TenseRule(sentences.get(i));
			System.out.println(sentences.get(i) + "\n"+ tenseRule.getTense());
		}
			
	}
}
