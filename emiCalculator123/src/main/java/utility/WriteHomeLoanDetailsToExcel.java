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

public class WriteHomeLoanDetailsToExcel {
	

		public static void writeHomeLoanDetailsToExcel(XSSFWorkbook workbookForHomeLoan, int testCaseCount, String[] year, String[] principalAmount, String[] interestAmount, String[] totalPayment, String[] balance, String[] loanPaidToDate) {
			//Create workbook			
			
			//Create worksheet
			XSSFSheet sheet = workbookForHomeLoan.createSheet("HomeLoanDetailsforTestCase"+testCaseCount);
			
			//Create data
			Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
			data.put("1", new Object[] {"Year","Principal Amount","Interest Amount","Total Payment","Balance","Loan Paid to Date"});
			for(int i = 0; i < year.length; i++) {
			data.put(""+(i+2)+"", new Object[] {year[i], principalAmount[i], interestAmount[i], totalPayment[i], balance[i], loanPaidToDate[i]});
			}
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
				FileOutputStream writefile = new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\HomeLoanOutputDetails.xlsx");
				try {
					workbookForHomeLoan.write(writefile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					writefile.close();
					System.out.println("\nHome Loan Sheet for new test case is created successfully\n");
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
