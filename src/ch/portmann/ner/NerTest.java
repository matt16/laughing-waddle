package ch.portmann.ner;

import java.util.HashMap;
import java.util.Map;

import ch.portmann.helper.FileHandler;
import ch.portmann.input.Sentence;
import ch.portmann.input.Text;
import ch.portmann.stanford.StanfordCore;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class NerTest {

	public static void main(String[] args) throws Exception {

		StanfordCore.init();

		FileHandler filehandler = new FileHandler();
		Map<String, Integer> freqMap = new HashMap<String, Integer>();
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		Map<String, Double> scoreMap = new HashMap<String, Double>();

		freqMap = filehandler.loadFileToMap("files/wordList/freqList.csv", true);

		System.out.println(freqMap.get("the"));

		String inputSentence = "Bank of America is one of the main beneficiaries of Trump's new economic paradigm with full-scale "
				+ "fiscal policies and the elimination of an excessive reliance on monetary policy.";

		Text text = new Text(inputSentence);

		// countWords
		for (Sentence s : text.getSentences()) {
			for (CoreLabel cl : s.getTokens()) {
				String lemma = cl.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
				if (countMap.containsKey(lemma)) {
					int count = countMap.get(lemma);
					count++;
					countMap.put(lemma, count);
				} else {
					countMap.put(lemma, 1);
				}
			}
		}

		// calc average freq
		double avgFreq = 44000; // ~avg of full sample

		for (String key : countMap.keySet()) {

			if (freqMap.get(key) != null)
				avgFreq = (avgFreq + freqMap.get(key)) / 2;

		}

		// calc average count
		double avgCount = 0;
		for (String key : countMap.keySet()) {
			avgCount = (avgCount + countMap.get(key)) / 2;
		}

		// calc score
		for (String key : countMap.keySet()) {

			double score = 0;

			if (freqMap.get(key) != null)
				score = (countMap.get(key) / avgCount) / (freqMap.get(key) / avgFreq);

			scoreMap.put(key, score);

		}

		// sort it
		scoreMap.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(10)
				.forEach(System.out::println);

	}

}