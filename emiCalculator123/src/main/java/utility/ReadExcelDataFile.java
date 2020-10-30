package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;



import org.apache.poi.xssf.usermodel.*;

public class ReadExcelDataFile {

	public String path;
	public FileInputStream fism = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	
	public ReadExcelDataFile(String path) {

		this.path = path;
		try {
			fism = new FileInputStream(path);
			workbook = new XSSFWorkbook(fism);
			sheet = workbook.getSheetAt(0);
			fism.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public String getCellData(String sheetName, int colNum, int rowNum) {
		String gg=null;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum - 1);
		if (row == null)
			return "";
		cell = row.getCell(colNum);
		if (cell == null)
			return "";

		switch (cell.getCellType())               
		{  
		case STRING:    //field that represents string cell type  
		   gg= cell.getStringCellValue();  
		    break; 
		case NUMERIC:    //field that represents number cell type  
		   gg = String.valueOf(cell.getNumericCellValue());  	
		   break; 
	  
		}
	       return gg;	
		} 
	



}
