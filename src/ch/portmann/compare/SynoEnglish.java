package ch.portmann.compare;

import java.io.IOException;

import ch.portmann.synonym.Synonym;
import ch.portmann.synonym.WordNet;

public class SynoEnglish extends NGramComparer implements LGTComparer {

	public SynoEnglish() {

	}

	@Override
	public CompareCouple compare(CompareCouple compareSet) {

		try {
			Synonym synoFactory = new WordNet();
			return ngramCompare(compareSet, 0, 5, synoFactory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}