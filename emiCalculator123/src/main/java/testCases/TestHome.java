package testCases;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


import emiCalculator_baseUI.BaseUI;
import utility.TestDataProvider;
import utility.ExtentReport;

public class TestHome extends BaseUI {
	
	@BeforeMethod
	public void getStarted() {
	
	}

	@DataProvider
	public Object[][] getDataTestOne(){
		return TestDataProvider.getTestData("ReadData.xlsx", "Feature", "Test Three");
	}
	
	@Test(dataProvider="getDataTestOne",priority=1)
	public  void testOne(Hashtable<String, String> dataTable) throws Exception {
		logger = report.createTest("Home loan by text");
		invokeproperties();
		invokeBrowser("browserName");
		getURL("websiteURL");
		Thread.sleep(3000);
		homeLoanTestCount++;
		selectAll("AmounttextH");
		loanAmountByText("AmounttextH",dataTable.get("Principal"));
		Thread.sleep(2000);
		selectAll("InteresttextH");
		interestRateByText("InteresttextH",dataTable.get("Rate"));
		selectAll("tenuretextH");
		loanTermByText("tenuretextH",dataTable.get("Tenure"));
		Thread.sleep(3500);
		displayHomeLoanEMI(dataTable.get("Tenure"));
		Thread.sleep(5000);
		tearDown();
}
	@DataProvider
	public Object[][] getDataTestTwo(){
		return TestDataProvider.getTestData("ReadData.xlsx", "Feature", "Test Four");
	}
	
	@Test(dataProvider="getDataTestTwo",priority=2)
	public void testTwo(Hashtable<String, String> dataTable) throws Exception
	{
		logger = report.createTest("Home loan by slider");
		invokeproperties();
		invokeBrowser("browserName");
		getURL("websiteURL");
		Thread.sleep(3000);
		homeLoanTestCount++;
		loanAmountBySliderHome("AmountsliderH",dataTable.get("Principal"));	
		interestRateBySlider("InterestsliderH",dataTable.get("Rate"));
		loanTermBySliderYearHome("tenuresliderH",dataTable.get("Tenure"));
		Thread.sleep(3500);
		displayHomeLoanEMI(dataTable.get("Tenure"));
		Thread.sleep(5000);
		tearDown();
	}
	@AfterTest
	public void endReport() {
		report.flush();
	}
}
