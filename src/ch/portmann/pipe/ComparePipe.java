package ch.portmann.pipe;

import java.util.ArrayList;
import java.util.List;

import ch.portmann.compare.CompareCouple;
import ch.portmann.compare.LGTComparer;

public class ComparePipe {

	// if this pipe is true the run is true -> no check
	private List<LGTComparer> killerPipe = new ArrayList<LGTComparer>();

	// this pipe mutate the sentence
	private List<LGTComparer> preprocessingPipe = new ArrayList<LGTComparer>();

	// if all this pipes are true the run is true
	private List<LGTComparer> andPipe = new ArrayList<LGTComparer>();

	// check if it was true so far -> without killerPipe
	private List<LGTComparer> checkPipe = new ArrayList<LGTComparer>();

	public ComparePipe() {

	}

	public CompareCouple run(CompareCouple compareCouple) {

		// loop killer feature
		for (LGTComparer comparer : killerPipe) {
			compareCouple = comparer.compare(compareCouple);
			if (compareCouple.getInput().isAllHit() && compareCouple.getSolution().isAllHit()) {
				return compareCouple;
			}
		}

		// loop preprocessingPipe
		for (LGTComparer comparer : preprocessingPipe) {
			compareCouple = comparer.compare(compareCouple);
		}

		// loop andPipe
		for (LGTComparer comparer : andPipe) {
			compareCouple = comparer.compare(compareCouple);
		}

		// loop checkPipe
		// THIS IF STATMENT IS DIFFRENT COMPARE <-> FEATURE
		if (compareCouple.getInput().isAllHit() && compareCouple.getSolution().isAllHit()) {
			for (LGTComparer comparer : checkPipe) {
				compareCouple = comparer.compare(compareCouple);
				if (!(compareCouple.getInput().isAllHit() && compareCouple.getSolution().isAllHit()))
					return compareCouple;
			}
		}

		return compareCouple;
	}

	public void addKillerPipe(LGTComparer comparer) {

		this.killerPipe.add(comparer);

	}

	public void addPreprocessingPipe(LGTComparer comparer) {

		this.preprocessingPipe.add(comparer);

	}

	public void addAndPipe(LGTComparer comparer) {

		this.andPipe.add(comparer);

	}

	public void addCheckPipe(LGTComparer comparer) {

		this.checkPipe.add(comparer);

	}

}
