
import java.util.ArrayList;

public class QuestionsAnswerEntity {

	// region Private Members
	private ArrayList<String> questions;
	private ArrayList<String> possibleAnswers;
	private ArrayList<String> answers;
	// endregion

	// region Public Members
	public QuestionsAnswerEntity(ArrayList<String> questions, ArrayList<String> possibleAnswers, ArrayList<String> answers) {
		this.questions = questions;
		this.possibleAnswers = possibleAnswers;
		this.answers = answers;
	}
	// endregion

	/**
	 * @return the questions
	 */
	public ArrayList<String> getQuestions() {
		return questions;
	}

	/**
	 * @return the possibleAnswers
	 */
	public ArrayList<String> getPossibleAnswers() {
		return possibleAnswers;
	}

	/**
	 * @return the answers
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(ArrayList<String> questions) {
		this.questions = questions;
	}

	/**
	 * @param possibleAnswers
	 *            the possibleAnswers to set
	 */
	public void setPossibleAnswers(ArrayList<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	/**
	 * @param answers
	 *            the answers to set
	 */
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	/**
	 * it evaluates if the answer given is the correct one
	 * 
	 * @param answer
	 * @param index
	 * @return
	 */
	public Boolean isCorrectAnswer(String answer, int index) {
		String value = this.answers.get(index).trim();
		if (value.equalsIgnoreCase(answer.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * get the following question
	 * 
	 * @param index
	 * @return
	 */
	public String getNextQuestion(int index) {
		return this.questions.get(index);
	}

	/**
	 * get the next set of answers
	 * 
	 * @param index
	 * @return
	 */
	public String[] getNextAnswers(int index) {
		String[] answers = this.possibleAnswers.get(index).split(";");
		return answers;
	}

	public Boolean contains(String value, int index) {
		String[] arr = getNextAnswers(index);
		for (int i = 0; i < arr.length; i++) {
			if (value.equalsIgnoreCase(arr[i])) {
				return true;
			}
		}
		return false;
	}
}