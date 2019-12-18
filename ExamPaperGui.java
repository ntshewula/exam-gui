/*
 * Author luvo
 *
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ExamPaperGui extends JPanel implements ActionListener {

	// region Private Members

	private static ArrayList<String> questions;
	private static ArrayList<String> possibleAnswers;
	private static ArrayList<String> answers;
	private QuestionsAnswerEntity qEntity;
	private FileProcessor processor;
	private JRadioButton radiobutton;
	private String name;
	private int index = 0;
	private ButtonGroup group;
	private JLabel question;
	private JPanel examPanel;
	private JPanel radiobtn, bttn;
	private JButton resultButton, previousButton;
	private JButton nextButton;
	private ArrayList<String[]> answered;
	private int count;

	private void setEnableRec(Component container, boolean enable) {
		try {
			Component[] components = ((Container) container).getComponents();
			for (int i = 0; i < components.length; i++) {
				components[i].setEnabled(enable);
			}
		} catch (ClassCastException e) {
			container.setEnabled(enable);
		}
	}

	private Boolean containsList(ArrayList<String[]> list, String[] arrlist) {

		String stringArray = toString(arrlist);
		for (String[] arr : list) {
			String stringlist = toString(arr);
			if (stringArray.equalsIgnoreCase(stringlist)) {
				return true;
			}
		}
		return false;
	}

	private String toString(String[] arr) {
		String stringArray = "";
		for (String text : arr) {
			stringArray += text;
		}
		return stringArray;
	}
	// endregion

	// region Public Members
	/**
	 * constructor of exam gui
	 * 
	 * @throws Exception
	 */
	public ExamPaperGui() throws Exception {
		/**************************************************** Call processfile method for Initialising my instance variables **************************************/
		answered = new ArrayList<String[]>();
		count = 0;
		processFile();

		/************************************************* Building The Initial radiobutton panel with first question *****************************/
		group = new ButtonGroup();
		question = new JLabel(qEntity.getNextQuestion(index));
		examPanel = new JPanel(new GridLayout(0, 1));
		radiobtn = new JPanel(new GridLayout(2, 2));
		examPanel.add(question);
		for (int i = 0; i < qEntity.getNextAnswers(index).length; i++) {
			String value = qEntity.getNextAnswers(index)[i];
			radiobutton = new JRadioButton(value);
			radiobutton.setMnemonic(KeyEvent.VK_B);
			radiobutton.setName(value);
			radiobutton.setActionCommand(value);
			group.add(radiobutton);
			radiobutton.addActionListener(this);
			if (i == 0) {
				radiobutton.setSelected(true);
				name = radiobutton.getName();
			}
			radiobtn.add(radiobutton);
		}

		examPanel.add(radiobtn);

		/************************************************* Building Buttons (previous, next, results) ****************************************************/

		nextButton = new JButton("Next");
		nextButton.addActionListener(this);

		previousButton = new JButton("Previous");
		previousButton.addActionListener(this);

		resultButton = new JButton("Result");
		resultButton.addActionListener(this);

		bttn = new JPanel(new GridLayout(1, 1));
		bttn.add(previousButton);
		bttn.add(resultButton);
		bttn.add(nextButton);
		examPanel.add(bttn);
		add(examPanel, BorderLayout.LINE_START);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	}

	/**
	 * perfom all the action perfomed on the GUI/Interface
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Result")) {
			System.out.println("Result button press Selected Button = " + name);
			if (name == "" || name == null) {
				JOptionPane.showMessageDialog(null, "Choose Answer");
			} else if (qEntity.isCorrectAnswer(name, index)) {
				JOptionPane.showMessageDialog(null, "Correct Answer");
				count++;
				String[] value = qEntity.getNextAnswers(index);
				answered.add(value);
				setEnableRec(radiobtn, false);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong Answer");
				answered.add(qEntity.getNextAnswers(index));
				setEnableRec(radiobtn, false);
			}
		}

		/************************************************* Building Panel for the next coming questions *****************************/
		if (e.getActionCommand().equals("Next")) {

			index++;
			if (index > questions.size() - 1) {
				JOptionPane.showMessageDialog(null, "No more questions left" + "\n" + "and you got " + count + "/" + questions.size() + " Answers");

				index--;
			} else {
				examPanel.remove(radiobtn);
				examPanel.remove(bttn);
				String[] nextanswer = qEntity.getNextAnswers(index);
				question.setText(qEntity.getNextQuestion(index));
				radiobtn = new JPanel(new GridLayout(2, 2));
				for (int i = 0; i < nextanswer.length; i++) {
					String value = nextanswer[i];
					radiobutton = new JRadioButton(value);
					radiobutton.setMnemonic(KeyEvent.VK_B);
					radiobutton.setName(value);
					radiobutton.setActionCommand(value);
					group.add(radiobutton);
					radiobutton.addActionListener(this);
					radiobtn.add(radiobutton);
					if (i == 0) {
						radiobutton.setSelected(true);
						name = radiobutton.getName();
					}
				}
				examPanel.add(radiobtn);
				nextButton = new JButton("Next");
				nextButton.addActionListener(this);

				previousButton = new JButton("Previous");
				previousButton.addActionListener(this);

				resultButton = new JButton("Result");
				resultButton.addActionListener(this);

				bttn = new JPanel(new GridLayout(1, 1));
				bttn.add(previousButton);
				bttn.add(resultButton);
				bttn.add(nextButton);
				examPanel.add(bttn);
				add(examPanel, BorderLayout.LINE_START);
				setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				if (containsList(answered, nextanswer)) {
					setEnableRec(radiobtn, false);
				}
			}

		}

		/************************************************* Building Panel for the previous coming questions *****************************/
		if (e.getActionCommand().equals("Previous")) {

			index--;
			if (index < 0) {
				JOptionPane.showMessageDialog(null, "This is the first question");
				index++;
			} else {
				examPanel.remove(radiobtn);
				examPanel.remove(bttn);
				String[] nextanswer = qEntity.getNextAnswers(index);
				question.setText(qEntity.getNextQuestion(index));
				radiobtn = new JPanel(new GridLayout(2, 2));
				for (int i = 0; i < nextanswer.length; i++) {
					String value = nextanswer[i];
					radiobutton = new JRadioButton(value);
					radiobutton.setMnemonic(KeyEvent.VK_B);
					radiobutton.setName(value);
					radiobutton.setActionCommand(value);
					group.add(radiobutton);
					radiobutton.addActionListener(this);

					radiobtn.add(radiobutton);
					if (i == 0) {
						radiobutton.setSelected(true);
						name = radiobutton.getName();
					}
				}
				examPanel.add(radiobtn);
				nextButton = new JButton("Next");
				nextButton.addActionListener(this);

				previousButton = new JButton("Previous");
				previousButton.addActionListener(this);

				resultButton = new JButton("Result");
				resultButton.addActionListener(this);

				bttn = new JPanel(new GridLayout(1, 1));
				bttn.add(previousButton);
				bttn.add(resultButton);
				bttn.add(nextButton);
				examPanel.add(bttn);
				add(examPanel, BorderLayout.LINE_START);
				setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				if (containsList(answered, nextanswer)) {
					setEnableRec(radiobtn, false);
				}
			}

		}
		if (qEntity.contains(e.getActionCommand(), index)) {
			JRadioButton btn = (JRadioButton) e.getSource();
			System.out.println("Selected Button = " + btn.getName());
			name = btn.getName();
		} else {

		}

	}

	/**
	 * prossess the file
	 * 
	 * @throws Exception
	 */
	public void processFile() throws Exception {
		FileProcessor processor = new FileProcessor("QuestionsAnswers.xlsx");
		qEntity = processor.processFile();
		questions = qEntity.getQuestions();
		possibleAnswers = qEntity.getPossibleAnswers();
		answers = qEntity.getAnswers();

	}

	/**
	 * main method that runs the whole
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		JFrame frame = new JFrame("Online Test of Java");
		frame.setSize(500, 300);
		frame.setLocation(300, 300);
		JComponent newContentPane = new ExamPaperGui();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		frame.setVisible(true);
	}

	// endregion

}
