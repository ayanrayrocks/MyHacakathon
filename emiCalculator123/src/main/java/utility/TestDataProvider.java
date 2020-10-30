package utility;

import java.util.Hashtable;



public class TestDataProvider {


	
	
	public static Object[][] getTestData(String DataFileName, String SheetName, String TestName) {

		ReadExcelDataFile readdata = new ReadExcelDataFile(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\"+ DataFileName);
		String sheetName = SheetName;
		String testName = TestName;

		//Find the name of testcase
		int startRowNum = 0;
		while (!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;
		}
		
		int startTestColumn = startRowNum + 1;
		int startTestRow = startRowNum + 2;

		//Count the no. of testcase
		int rows = 0;
		while (!readdata.getCellData(sheetName, 0, startTestRow + rows).equals("")) {
			rows++;
		}
		
		//Count the no. of colmns
		int colmns = 0;
		while (!readdata.getCellData(sheetName, colmns, startTestColumn).equals("")) {
			colmns++;
		}
		
		
		Object[][] dataSet = new Object[rows][1];
		Hashtable<String, String> dataTable = null;
		int dataRowNumber=0;
		for (int rowNumber = startTestRow; rowNumber <= startTestColumn + rows; rowNumber++) {
			dataTable = new Hashtable<String, String>();
			for (int colNumber = 0; colNumber < colmns; colNumber++) {
				String key = readdata.getCellData(sheetName, colNumber, startTestColumn);
				//System.out.println(key);
				String value = readdata.getCellData(sheetName, colNumber, rowNumber);
				dataTable.put(key, value);
			
			}
			dataSet[dataRowNumber][0]=dataTable;
			dataRowNumber++;
		}

	 return dataSet;
	}
}
