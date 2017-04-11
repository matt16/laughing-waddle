package ch.portmann.pipe;

import java.util.ArrayList;
import java.util.List;

import ch.portmann.feature.FSurprise;
import ch.portmann.feature.FAnxiety;
import ch.portmann.feature.FConditionality1;
import ch.portmann.feature.FConditionality2;
import ch.portmann.feature.FDisappointment;
import ch.portmann.feature.FFear;
import ch.portmann.feature.FHope;
import ch.portmann.feature.FRelief;
import ch.portmann.feature.FUncertainty;
import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;
import ch.portmann.input.Text;
import ch.portmann.input.Text2;

public class RunFeaturePipe {

	String inputS = "";

	public RunFeaturePipe(String inputS) {

		this.inputS = inputS;

	}


//	public List<Sentence> process() throws Exception {
	public List<Sentence2> process() throws Exception {
		FeaturePipe featurePipe = new FeaturePipe();
		
		// HERE YOU CAN DEFINE THE FEATURES
		//featurePipe.addFeature(new FSurprise());
		//featurePipe.addFeature(new FAnxiety());
		featurePipe.addFeature(new FConditionality1());
		featurePipe.addFeature(new FConditionality2());
//		featurePipe.addFeature(new FDisapointment());
//		featurePipe.addFeature(new FFear());
		//featurePipe.addFeature(new FHope());
		//featurePipe.addFeature(new FRelief());
//		featurePipe.addFeature(new FSurprise());
		//featurePipe.addFeature(new FUncertainty());

//		  This probably triggers the Annotations
//		Text inputR = new Text(inputS);
//		List<Sentence> output = new ArrayList<Sentence>();
//
//		for (int i = 0; i < inputR.getSentences().size(); i++) {
//
//			Sentence input = inputR.getSentences().get(i);
//			output.add(featurePipe.run(input));
//
//		}
		
		
		Text2 inputR = new Text2(inputS);
		List<Sentence2> output = new ArrayList<Sentence2>();

		for (int i = 0; i < inputR.getSentences().size(); i++) {

			Sentence2 input = inputR.getSentences().get(i);
			output.add(featurePipe.run(input));

		}
		
		return output;
		
	}
}