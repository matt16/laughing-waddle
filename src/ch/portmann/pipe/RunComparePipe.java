package ch.portmann.pipe;

import java.util.Arrays;

import ch.portmann.compare.CompareCouple;
import ch.portmann.compare.HandMadeEnglish;
import ch.portmann.compare.NumExpressions;
import ch.portmann.compare.OneToOne;
import ch.portmann.compare.QuantExpressions;
import ch.portmann.compare.SynoEnglish;
import ch.portmann.input.Sentence;
import ch.portmann.input.Text;
import ch.portmann.stanford.StanfordCore;

public class RunComparePipe {
	
	String inputS = "";
	String solutionS = "";

	public RunComparePipe(String inputS, String solutionS) {
		
		this.inputS = inputS;
		this.solutionS = solutionS;
		
	}

	public void process() throws Exception {

		StanfordCore.init();

		ComparePipe comparePipe = new ComparePipe();

		// killers
		comparePipe.addKillerPipe(new OneToOne());
		//comparePipe.addKillerPipe(new SupportedLang());

		// preprocessing
		// ---

		// and pipes
		comparePipe.addAndPipe(new NumExpressions());
		comparePipe.addAndPipe(new QuantExpressions());
		comparePipe.addAndPipe(new SynoEnglish());
		comparePipe.addAndPipe(new HandMadeEnglish());

		// check pipe
		// ---
		
		
		Text inputR = new Text(inputS);
		Text solutionR = new Text(solutionS);
		// check if both texts have the same length

		if (inputR.getSentences().size() == solutionR.getSentences().size()) {

			for (int i = 0; i < inputR.getSentences().size(); i++) {

				Sentence input = inputR.getSentences().get(i);
				Sentence solution = solutionR.getSentences().get(i);

				CompareCouple compareCouple = comparePipe.run(new CompareCouple(input, solution));
				
				System.out.println(compareCouple.getMessage());
				System.out.println("Input:");
				System.out.println(compareCouple.getInput().getOrginalSentence());
				System.out.println(Arrays.toString(compareCouple.getInput().getGroupes()));
				System.out.println(Arrays.toString(compareCouple.getInput().getIsHit()));
				System.out.println("Solution:");
				System.out.println(compareCouple.getSolution().getOrginalSentence());
				System.out.println(Arrays.toString(compareCouple.getSolution().getGroupes()));
				System.out.println(Arrays.toString(compareCouple.getSolution().getIsHit()));
				
			}
		}
	}
}