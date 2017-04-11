package ch.portmann.main;

import java.io.File;

import ac.biu.nlp.normalization.BiuNormalizer;

public class Test2 {

	

	/**
	 * A demo of the BIU normalizer. Add your tests to the list below.
	 * 
	 * 
	 * @param args
	 *            - the full path of the string rules file. The default file is
	 *            called "string_rules.txt".
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File stringRulesFile = new File("files/stringRules/string_rules.txt");

		BiuNormalizer n = new BiuNormalizer(stringRulesFile);

		// === Tests ===
		String s = "Fed's Fischer: Fed Likely To Raise Rates Before Year's End - Federal Reserve Vice-Chairman Stanley Fischer said Monday the U.S. central bank remains on track to raise short-term rates this year, in a speech that warned future rate increases are unlikely to move steadily upwards, as they did in past cycles when the Fed boosted borrowing costs. Federal Reserve Vice Chairman Stanley Fischer said Monday he expects the central bank to raise short-term interest rates this year. 'An increase in the target federal funds range likely will be warranted before the end of the year,” Mr. Fischer said. The decision to raise rates will be informed by 'a wide range of information regarding labor market conditions, inflation, and financial and international developments,” he said. The Fed will be confident it can raise rates when there has been 'further improvement in the labor market and we are reasonably confident” inflation will rise back to the Fed's 2% target, the official said. But Mr. Fischer also cautioned that the gradual rate rise paths seen when the Fed increased borrowing costs in the middle of the last decade are very unlikely to be repeated in the coming move to put monetary policy back on a more historically normal footing. 'A smooth path upward in the federal funds rate will almost certainly not be realized, because, inevitably, the economy will encounter shocks” that policymakers and others do not see coming, Mr. Fischer said. He noted that once rates begin to rise, they could easily move 'up and down” depending on how the economy performs. Mr. Fischer's comments came from a text prepared for delivery before an event held by the Economics Club of New York. He is scheduled to take questions following the address. The speech Monday was Mr. Fischer's first since the Fed met last week in a gathering that continued to prepare the way for interest rate increases from current near-zero levels at some point later in the year. At that meeting, officials dropped their pledge to remain 'patient” on rate rises, and signaled that an active debate over when to boost borrowing costs begins with the mid-June policy meeting. At a press conference after the Fed meeting Wednesday, Chairwoman Janet Yellen said 'while it's still the case that we consider it unlikely that economic conditions will warrant an increase in the target range at the April meeting, such an increase could be warranted at any later meeting, depending on how the economy evolves.” Fed officials are weighing rate rises amid ongoing solid gains in the labor market. Complicating their decision is a round of mixed economic data to start the year, and inflation that continues to fall well below the Fed's 2% price rise target. A number of Fed officials have signaled they'll be willing to raise rates even with inflation on the weak side, but some central bankers believe that would be a mistake. Ms. Yellen is also due to speak on monetary policy issues in a speech in San Francisco on Friday. In his speech, Mr. Fisher also there is 'considerable uncertainty” about the final destination of the Fed's rate rise campaign. He said that as the Fed moves to raise rates, it will be sure to note changes in rates are designed to achieve the Fed's job and inflation goals, and will respond to the economic data. Mr. Fischer also said policy rules have value in informing monetary policy decisions, but are only a part of the puzzle. Mr. Fischer noted in his speech the current 5.5% jobless rate is close to the 'natural” level of unemployment in the U.S. He said the Fed continues to expect progress is getting inflation back to 2%. He said low productivity in the U.S. likely has something to do with the more rapid improvement in the job market than many had thought likely, while leading to lower-than-expected activity gains. Mr. Fischer also said the European Central Bank's bond-buying stimulus program is likely to help the U.S. over the longer run. The buying is helping push down U.S. interest rates, he noted. As for the dollar, the strength of which has concerned some, Mr. Fischer said gains there likely reflect 'other factors” beyond the impact of the ECB actions, 'including the relatively strong performance of the U.S. economy.” Real Time Economics offers exclusive news, analysis and commentary on the U.S. and global economy, central bank policy and economics.";
		System.out.println(n.normalize(s));
	
	}
}
