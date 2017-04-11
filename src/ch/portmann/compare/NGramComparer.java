package ch.portmann.compare;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import ch.portmann.input.TokenLight;
import ch.portmann.synonym.Synonym;

public class NGramComparer {

	public NGramComparer() {

	}

	public CompareCouple ngramCompare(CompareCouple compareCouple, int startGroup, int ngram, Synonym synonymFactory)
			throws IOException {

		Integer inputStartPoint = compareCouple.getInput().getGroupRangeByGNum(startGroup)[0];
		Integer solutionStartPoint = compareCouple.getSolution().getGroupRangeByGNum(startGroup)[0];

		// is startgroup is grouped -> skip this group
		if (compareCouple.getInput().getGroupRangeByGNum(startGroup)[1]
				- compareCouple.getInput().getGroupRangeByGNum(startGroup)[0] == 0) {
			if (compareCouple.getSolution().getGroupRangeByGNum(startGroup)[1]
					- compareCouple.getSolution().getGroupRangeByGNum(startGroup)[0] == 0) {

				// input
				for (Integer iInput = ngram; iInput >= 0; iInput--) {
					// check if it is grouped
					Integer[] inputRange = new Integer[] { inputStartPoint, inputStartPoint + iInput };
					if (compareCouple.getInput().isUngrouped(inputRange)) {
						// get ngram lemma string

						// check if its is to big ngram INPUT
						if (inputRange[1] < compareCouple.getInput().getSize()) {

							//get lemma ngrams
							TokenLight compareToken = compareCouple.getInput().getLemmaNgram(inputRange[0], inputRange[1]);
							// get synos
							Set<List<TokenLight>> synoResult = synonymFactory.getSynos(compareToken);
							//get orginal ngrams
							compareToken = compareCouple.getInput().getOrginalNgram(inputRange[0], inputRange[1]);
							// get synos
							synoResult.addAll(synonymFactory.getSynos(compareToken));
							
							//get orginal ngrams
							System.out.println(compareToken.getStemmed());
							System.out.println(compareToken.getPos());
							System.out.println("");

							for (List<TokenLight> lt : synoResult) {

								// check if SOLUTION in range is grouped
								Integer[] soulutionRange = new Integer[] { solutionStartPoint,
										solutionStartPoint + lt.size() };

								int iSolution = -1;
								boolean checkFlag = true;

								for (TokenLight t : lt) {
									iSolution++;
									// check Inputcompare string to large for
									// SolutionCompare
									if (compareCouple.getSolution().getSize() > solutionStartPoint + iSolution) {
										System.out.println(compareCouple.getSolution().getLemmas()
												.get(solutionStartPoint + iSolution).toLowerCase() + "<->"
												+ t.getStemmed().toLowerCase());

										//compare every token
										if (!compareCouple.getSolution().getLemmas().get(solutionStartPoint + iSolution)
												.toLowerCase().equals(t.getStemmed().toLowerCase())) {
											if (!compareCouple.getSolution().getOrginalString().get(solutionStartPoint + iSolution)
													.toLowerCase().equals(t.getStemmed().toLowerCase())) {

											checkFlag = false;
											}
										}
									} else {
										checkFlag = false;
									}
								}

								if (checkFlag) {

									// check if is ungrouped
									if (compareCouple.getInput()
											.isUngrouped(new Integer[] { inputStartPoint, inputStartPoint + iInput })
											&& compareCouple.getSolution().isUngrouped(new Integer[] {
													solutionStartPoint, solutionStartPoint + iSolution })) {
										compareCouple.getInput().setHitandGroupe(
												new Integer[] { inputStartPoint, inputStartPoint + iInput });
										compareCouple.getSolution().setHitandGroupe(
												(new Integer[] { solutionStartPoint, solutionStartPoint + iSolution }));
										System.out.println("TRUE");
										System.out.println("inputStartPoint: " + inputStartPoint);
										System.out.println("iInput: " + iInput);
										System.out.println("solutionStartPoint: " + solutionStartPoint);
										System.out.println("iSolution: " + iSolution);
										break;
									}
								}
								System.out.println("---------");
							}

							System.out.println("---------");
							System.out.println("---------");
						}
					}
				}
			}
		}
		if ((startGroup + 1) <= compareCouple.getInput().getNumOfGroupes()) {
			if ((startGroup + 1) <= compareCouple.getSolution().getNumOfGroupes()) {
				ngramCompare(compareCouple, startGroup + 1, ngram, synonymFactory);
			}
		}
		System.out.println("---------");
		System.out.println("-THE END-");
		System.out.println("---------");
		return compareCouple;
	}
}