package utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteCarLoanDetailsToExcel {

	public static void writeCarLoanDetailsToExcel(XSSFWorkbook workbookForCarLoan, int testCaseCount, String principalAmount, String interestAmount) {
		//Create workbook			
		
		//Create worksheet
		XSSFSheet sheet = workbookForCarLoan.createSheet("CarLoanDetailsforTestCase"+testCaseCount);
		
		//Create data
		Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
		data.put("1", new Object[] {"Principal Amount","Interest Amount"});
		data.put("2", new Object[] {principalAmount, interestAmount});
		
		
		//Iterate over the data
		Set<String> set = data.keySet();
		int rownum = 0;
		
		for(String key: set) {
			Row row = sheet.createRow(rownum++);
			
			Object[] details = data.get(key);
			int cellnum = 0;
			
			for(Object value : details) {
				Cell cell = row.createCell(cellnum++);
				
				if(value instanceof String) {
					cell.setCellValue((String)value);
				}
			}
			
		}
		
		try {
			FileOutputStream writefile = new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\CarLoanOutputDetails.xlsx");
			try {
				workbookForCarLoan.write(writefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writefile.close();
				System.out.println("\nCar Loan Sheet for new test case is created successfully\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
