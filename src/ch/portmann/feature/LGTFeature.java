package ch.portmann.feature;

import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;

public interface LGTFeature {

	
	public Sentence run(Sentence sentence);

	/*
	 * This function is used to create .Json file without property group. 
	 * It replaces the original class Sentence
	 */
	public Sentence2 run(Sentence2 sentence);
	

}