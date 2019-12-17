
//Author : Luvo Ntshewula
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileProcessor {

	// region Private Members
	private String filename;
	private ArrayList<String> questions;
	private ArrayList<String> possibleAnswers;
	private ArrayList<String> answers;
	// endregion

	// region Public Members
	public FileProcessor(String filename) {
		this.filename = filename;
		this.questions = new ArrayList<>();
		this.possibleAnswers = new ArrayList<>();
		this.answers = new ArrayList<>();
	}

	/**
	 * read the excel file and return the object of it
	 * 
	 * @return
	 * @throws Exception
	 */
	public QuestionsAnswerEntity processFile() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File(this.filename));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Row nextRow = iterator.next();
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			String rowValue = "";
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				rowValue += cell.getStringCellValue();
				rowValue += " - ";
			}

			String[] rowValues = rowValue.split(" - ");
			this.questions.add(rowValues[0]);
			this.possibleAnswers.add(rowValues[1]);
			this.answers.add(rowValues[2]);
		}
		workbook.close();
		inputStream.close();

		QuestionsAnswerEntity questionsAnswerEntity = new QuestionsAnswerEntity(questions, possibleAnswers, answers);
		return questionsAnswerEntity;
	}

	// endregion
}
