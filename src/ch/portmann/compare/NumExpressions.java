package ch.portmann.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.portmann.input.Sentence;
import ch.portmann.wolframalpha.Wolframalpha;

public class NumExpressions implements LGTComparer {

	public class MExpression {
		private String expression;
		private Integer[] startEndPosition;

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}

		public Integer[] getStartEndPosition() {
			return startEndPosition;
		}

		public void setStartEndPosition(Integer[] startEndPosition) {
			this.startEndPosition = startEndPosition;
		}

	}

	public NumExpressions() {

	}

	@Override
	public CompareCouple compare(CompareCouple compareCouple) {

		List<MExpression> mExpressionsInput = getQuantExStrings(compareCouple.getInput());
		List<MExpression> mExpressionsSolution = getQuantExStrings(compareCouple.getSolution());

		// Check if equal
		Wolframalpha wa = new Wolframalpha();

		for (int i = 0; i < mExpressionsInput.size(); i++) {
			if (mExpressionsSolution.size() - 1 >= i) {
				if (wa.isEqual(mExpressionsInput.get(i).getExpression(), mExpressionsSolution.get(i).getExpression(),
						true)) {

					if (mExpressionsInput.get(i).getExpression().length() * 0.8 - 2 > mExpressionsSolution.get(i)
							.getExpression().length()) {
						compareCouple.setMessage(1);
					} else if (mExpressionsInput.get(i).getExpression().length() * 1.2 + 2 < mExpressionsSolution.get(i)
							.getExpression().length()) {
						compareCouple.setMessage(2);
					}

					else {

						compareCouple.getInput().setHitandGroupe(mExpressionsInput.get(i).getStartEndPosition());
						compareCouple.getSolution().setHitandGroupe(mExpressionsSolution.get(i).getStartEndPosition());
						compareCouple.setMessage(0);

					}

				}

			}
		}

		return compareCouple;
	}

	public List<MExpression> getQuantExStrings(Sentence s) {

		// Mathjax expressions
		List<MExpression> mExpressions = new ArrayList<MExpression>();

		Pattern pattern = Pattern.compile("[0-9()+\\-*\\/.,_,^]+[0-9()+\\-*\\/.,_,^]*");
		Matcher matcher = pattern.matcher(s.getOrginalSentence());

		// Check all occurrences
		while (matcher.find()) {

			MExpression mExpression = new MExpression();

			Integer[] tempPos = new Integer[] { matcher.start(), matcher.end() };
			tempPos = s.convertSentencePosToStartEnd(tempPos);
			
			System.out.println(s.getString(tempPos[0], tempPos[1]).replace(" ", ""));

			if (s.isUngrouped(tempPos)) {

				// replace spaces -> wolframalpha cant handle it
				mExpression.startEndPosition = tempPos;
				mExpression.expression = s.getString(tempPos[0], tempPos[1]).replace(" ", "");
				mExpressions.add(mExpression);
			}

		}

		return mExpressions;
	}

}
