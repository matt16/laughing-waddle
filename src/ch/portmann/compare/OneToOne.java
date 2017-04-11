package ch.portmann.compare;

/***
 * This class don't need Stanford parsing
 * 
 */
public class OneToOne implements LGTComparer {

	public OneToOne() {

	}

	@Override
	public CompareCouple compare(CompareCouple compareCouple) {

		if (compareCouple.getInput().getOriginSentence().equals(compareCouple.getSolution().getOriginSentence())) {
			
			compareCouple.getInput().setHitandGroupe(new Integer[] { 0, compareCouple.getSizeInput() - 1 });
			compareCouple.getSolution().setHitandGroupe(new Integer[] { 0, compareCouple.getSizeSolution() - 1 });
			compareCouple.setMessage(0);
			
		}

		return compareCouple;

	}
}