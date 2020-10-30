package testCases;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utility.TestDataProvider;
 import utility.ExtentReport;
import emiCalculator_baseUI.BaseUI;

public class TestCar extends BaseUI {
	
   public WebDriver driver;
	
	
	
	@DataProvider
	public Object[][] getDataTestOne(){
		
		return TestDataProvider.getTestData("ReadData.xlsx", "Feature", "Test One");
	}
	
	@Test(dataProvider="getDataTestOne",priority=1)
	public void TestOne(Hashtable<String, String> dataTable) throws Exception {
		logger = report.createTest("Car loan by Slider");
		Thread.sleep(3000);
		invokeproperties();
		invokeBrowser("Chrome");
		getURL("websiteURL");
		carLoanTestCount++;
		navigateTo("navigatebutton");
		loanAmountBySliderCar("amountBySlider",dataTable.get("Principal"));
	    interestRateBySlider("rateBySlider",dataTable.get("Rate"));
	    loanTermBySliderYearCar("termBySliderYear",dataTable.get("Tenure"));
	    Thread.sleep(2500);
	    displayCarLoanEMI("firstYearKey","firstMonthPrincipalAmt","firstMonthInterestAmt",dataTable.get("Tenure"));
		Thread.sleep(5000);
		tearDown();
		
	}
	@DataProvider
	public Object[][] getDataTestTwo(){
		return TestDataProvider.getTestData("ReadData.xlsx", "Feature", "Test Two");
	}
	
	@Test(dataProvider="getDataTestTwo",priority=2)
	public void TestTwo(Hashtable<String, String> dataTable) throws Exception {
		logger = report.createTest("Car loan by text");
		invokeproperties();
		invokeBrowser("Chrome");
		getURL("websiteURL");
		carLoanTestCount++;
		navigateTo("navigatebutton");
		selectAll("amountByText");
		loanAmountByText("amountByText",dataTable.get("Principal"));
		selectAll("rateByText");
		interestRateByText("rateByText",dataTable.get("Rate"));
		selectAll("termByText");		
		loanTermByText("termByText",dataTable.get("Tenure"));
		Thread.sleep(2500);
	    displayCarLoanEMI("firstYearKey","firstMonthPrincipalAmt","firstMonthInterestAmt",dataTable.get("Tenure"));
		Thread.sleep(5000);
		tearDown();
	}
	@AfterTest
	public void endReport() {
		report.flush();
	}

}
