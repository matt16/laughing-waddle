package ch.portmann.compare;

import java.io.IOException;

import ch.portmann.synonym.HandMade;
import ch.portmann.synonym.Synonym;

public class HandMadeEnglish extends NGramComparer implements LGTComparer {

	public HandMadeEnglish() {

	}

	@Override
	public CompareCouple compare(CompareCouple compareSet) {

		try {
			Synonym synoFactory = new HandMade("files/handmadeSynos/englishSynos.txt");
			return ngramCompare(compareSet, 0, 3, synoFactory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
