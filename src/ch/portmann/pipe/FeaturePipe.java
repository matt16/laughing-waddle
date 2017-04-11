package ch.portmann.pipe;

import java.util.ArrayList;
import java.util.List;

import ch.portmann.feature.LGTFeature;
import ch.portmann.feature.RegexFeature;
import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;

public class FeaturePipe {

	private List<LGTFeature> featurePipe = new ArrayList<LGTFeature>();
	private List<String> featurePipeTypes = new ArrayList<>();


	public FeaturePipe() {

	}

	public Sentence run(Sentence sentence) {

		for (LGTFeature feature : featurePipe) {
			sentence = feature.run(sentence);
		}

		return sentence;

	}
	
	/**
	 * @param sentence: The input is the sentence to be annotated, all the feature in the featurePipe will be extracted in the sentence.
	 * 
	 * @return sentence: The output is the sentence with extracted information.
	 */
	public Sentence2 run(Sentence2 sentence) {

		for (LGTFeature feature : featurePipe) {
			sentence = feature.run(sentence);
		}

		return sentence;

	}

	public void addFeature(RegexFeature feature) {

		this.getFeaturePipe().add(feature);
		this.getFeaturePipeTypes().add(feature.toString());

	}

	public List<LGTFeature> getFeaturePipe() {
		return this.featurePipe;
	}

	public void setFeaturePipe(List<LGTFeature> featurePipe) {
		this.featurePipe = featurePipe;
	}
	
	public List<String> getFeaturePipeTypes(){
		return this.featurePipeTypes;
	}
}