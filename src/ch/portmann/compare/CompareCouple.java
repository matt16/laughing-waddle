package ch.portmann.compare;

import ch.portmann.input.Sentence;
import ch.portmann.messenger.Messenger;

//all the equal things add to sentence
public class CompareCouple {

	private Sentence inputSentence;
	private Sentence solutionSentence;
	private Messenger messenger = new Messenger();

	public CompareCouple(Sentence inputSentence, Sentence solutionSentence) {
		this.inputSentence = inputSentence;
		this.solutionSentence = solutionSentence;
	}

	public Sentence getInput() {
		return inputSentence;
	}

	public void setInput(Sentence inputSentence) {
		this.inputSentence = inputSentence;
	}

	public Sentence getSolution() {
		return solutionSentence;
	}

	public void setSolution(Sentence solutionSentence) {
		this.solutionSentence = solutionSentence;
	}

	public int getSizeInput() {
		return this.getInput().getSize();
	}

	public int getSizeSolution() {
		return this.getSolution().getSize();
	}

	public String getMessage() {
		return messenger.getMessage();
	}

	public void setMessage(int messageIndex) {
		this.messenger.setMessage(messageIndex);
	}

}
