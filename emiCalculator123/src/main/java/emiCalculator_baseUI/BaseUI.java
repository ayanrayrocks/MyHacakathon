package emiCalculator_baseUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utility.DateUtil;
import utility.ExtentReport;

public class BaseUI<softAssert> {
	public ExtentTest logger;
	public ExtentReports report=ExtentReport.getReportInstance(); 
	//SoftAssert softAssert = new SoftAssert();
	public WebDriver driver;
	public Properties prop;
	public static int carLoanTestCount = 0;
	public static int homeLoanTestCount = 0;
	XSSFWorkbook workbookForCarLoan = new XSSFWorkbook();
	XSSFWorkbook workbookForHomeLoan = new XSSFWorkbook();
	static String nodeUrl;
	
public void invokeproperties() {
		
		if (prop == null) {
			prop = new Properties();
		
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "//src//test//resources//ObjectRepository//projectConfig.properties");
				prop.load(file);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	 
         }
	}

		public void invokeBrowser(String Browsername) {
		 try {
			 if(Browsername.equalsIgnoreCase("Chrome")) {
				/* ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-notifications");
				 System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");				
	                driver = new ChromeDriver(options);*/
	                
	                nodeUrl="http://192.168.29.71:5566/wd/hub";
	    		 	DesiredCapabilities capabilities=DesiredCapabilities.chrome();
	    		 	driver=new RemoteWebDriver(new URL(nodeUrl),capabilities);
			 } else {
				/* FirefoxOptions options = new FirefoxOptions();
					options.addArguments("--disable-notifications");
				 System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\geckodriver.exe");
	                driver = new FirefoxDriver(options);*/
				 
	                nodeUrl="http://192.168.29.71:5567/wd/hub";
	    		 	DesiredCapabilities capabilities=DesiredCapabilities.firefox();
	    		 	driver=new RemoteWebDriver(new URL(nodeUrl),capabilities);
			 }
			 
			 
			 
			 
		 }catch(Exception e) { 
			 
		 }
			    driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
	}
	
	public void getURL(String websiteURLKey) {
		try {
		driver.get(prop.getProperty(websiteURLKey));
		reportPass(" Identified Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	
	}
	
	public void navigateTo(String navKey) {
		try {
		 driver.findElement(By.xpath(prop.getProperty(navKey))).click();
		 reportPass(navKey + " navigated Successfully");
	}catch (Exception e) {
		reportFail(e.getMessage());
	}
	}
	

	
	public void loanAmountBySliderCar(String amountKey, String m1) {
		double d = Double.parseDouble(m1);
		try {
		if(d>0&&d<700000) { 
			  
			  double g1 = (d*3.2)/10000;
			  double g2 = Math.round(g1);
			  int g3 = (int) g2;
			  WebElement slider = driver.findElement(By.xpath(prop.getProperty(amountKey)));
			  logger.log(Status.INFO, "Amount Locater1 Identified  : " + amountKey);
			  Actions action = new Actions(driver);
			  action.dragAndDropBy(slider,-130,0).perform();
			  action.dragAndDropBy(slider,g3,0).perform();
            } else if(d>=700000) {
              double d1 = d+10000;
  			  double g1 = (d1*3.2)/10000;
  			  double g2 = Math.round(g1);
  			  int g3 = (int) g2;
  			 WebElement slider = driver.findElement(By.xpath(prop.getProperty(amountKey)));
  			  logger.log(Status.INFO,"Amount Locater2 Identified : " + amountKey);
			  Actions action = new Actions(driver);
			  action.dragAndDropBy(slider,-130,0).perform();
			  action.dragAndDropBy(slider,g3,0).perform();
            }else {
    			reportFail("Failing the Testcase, Invalid Amount " + amountKey);
    		}reportPass("Passing the Testcase, Valid Amount for Car Loan" + amountKey);
		}catch (Exception e) {

    			// Fail the TestCase and Report the error
    			reportFail(e.getMessage());
    			e.printStackTrace();
    		}

            	
            
	}
	
	public void interestRateBySlider(String ratekey,String m1) {
		double d = Double.parseDouble(m1);
		try {
		if(d>5) { 
		  double g1 = d-5.0;
		  double g2 = g1*44;
		  int g3 = (int) g2;
		  WebElement slider = driver.findElement(By.xpath(prop.getProperty(ratekey)));
		  logger.log(Status.INFO, " Interest Rate Locater1 Identified : " + ratekey);
		  Actions action = new Actions(driver);
		  action.dragAndDropBy(slider,-330,0).perform();
		  action.dragAndDropBy(slider,g3,0).perform();
		 }else if(d==5) {
			 WebElement slider = driver.findElement(By.xpath(prop.getProperty(ratekey)));
			 logger.log(Status.INFO, "Interest Rate Locater2 Identified : " + ratekey);
			 Actions action = new Actions(driver);
			 action.dragAndDropBy(slider,-330,0).perform();
		}else {
			reportFail("Failing the Testcase,Invalid Interest rate " + ratekey);
		}reportPass("Passing the Testcase, Valid Interest Rate " + ratekey);
	}catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();
		}

		
	}
	
	public void loanTermBySliderYearCar(String termkey,String m1) {
		double m = Double.parseDouble(m1); 
		try {
		if(m>1) {
			  double g1 = m-1;
			  double g2 = g1*90;
			  int g3 = (int) g2;
			  WebElement slider = driver.findElement(By.xpath(prop.getProperty(termkey)));
			  logger.log(Status.INFO, "Loan term Locater1 Identified : " + termkey);
			  Actions action = new Actions(driver);
			  action.dragAndDropBy(slider,-360,0).perform();
			 action.dragAndDropBy(slider,g3,0).perform();
			 }else if(m==1) {
				 WebElement slider = driver.findElement(By.xpath(prop.getProperty(termkey)));
				 logger.log(Status.INFO, "Loan Term Locater2 Identified : " + termkey);
				 Actions action = new Actions(driver);
				 action.dragAndDropBy(slider,-360,0).perform();
			}else {
				reportFail("Failing the Testcase, Invalid Loan Term " + termkey);
			}reportPass("Passing the Testcase, Valid Loan Term for Car Loan " + termkey);
		}catch (Exception e) {

				// Fail the TestCase and Report the error
				reportFail(e.getMessage());
				e.printStackTrace();
			}

		}
	
	public void loanAmountBySliderHome(String AmountsliderH, String m1) {
		double d = Double.parseDouble(m1);
		try {
		if(d>0)
		{
				  double g1 = (d*3.2)/100000;
				  double g2 = Math.round(g1);
				  int g3 = (int) g2;
				  WebElement slider = driver.findElement(By.xpath(prop.getProperty(AmountsliderH)));
				  logger.log(Status.INFO, "Loan Amount Locater1 Identified : " + AmountsliderH);
				  Actions action = new Actions(driver);
				  action.dragAndDropBy(slider,-200,0).perform();
				  action.dragAndDropBy(slider,g3,0).perform();
		} else 
		{
			WebElement slider = driver.findElement(By.xpath(prop.getProperty(AmountsliderH)));
			logger.log(Status.INFO, "Loan Amount Locater2 Identified : " + AmountsliderH);
			 Actions action = new Actions(driver);
			 action.dragAndDropBy(slider,-200,0).perform();
		}
		reportPass("Passing the Testcase, Valid Loan Amount for Home Loan " + AmountsliderH);
			
		
	}catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();
		}

	}
		
	
	public void loanTermBySliderYearHome(String tenuresliderH,String m1) {
		double m = Double.parseDouble(m1);
		try {
		if(m>1) {
			  double g1 = m-1;
			  double g2 = g1*20;
			  int g3 = (int) g2;
			  WebElement slider = driver.findElement(By.xpath(prop.getProperty(tenuresliderH)));
			  logger.log(Status.INFO, "Loan Term Locater1 Identidied : " + tenuresliderH);
			  Actions action = new Actions(driver);
			  action.dragAndDropBy(slider,-450,0).perform();
			action.dragAndDropBy(slider,g3,0).perform();
			 }else if(m==1) {
				 WebElement slider = driver.findElement(By.xpath(prop.getProperty(tenuresliderH)));
				 logger.log(Status.INFO, "Loan Term Locater2 Identified : " + tenuresliderH);
				 Actions action = new Actions(driver);
				 action.dragAndDropBy(slider,-450,0).perform();
			}else {
				reportFail("Failing the Testcase, Invalid Loan Term for Home Loan" +tenuresliderH );
			}reportPass("Passing the Testcase, Valid Loan Term for Home Loan " +tenuresliderH);
		}catch (Exception e) {

				// Fail the TestCase and Report the error
				reportFail(e.getMessage());
				e.printStackTrace();
			}

		}
	
	public void selectAll(String byKeys) {
		try {
		WebElement input = driver.findElement(By.xpath(prop.getProperty(byKeys)));
		logger.log(Status.INFO, "Locater Identified : " +byKeys);
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.chord(Keys.COMMAND, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(Keys.BACK_SPACE);
		}
	    catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void loanAmountByText(String amountKey,String data) {
		try {
		 driver.findElement(By.xpath(prop.getProperty(amountKey))).sendKeys(data);
		 reportPass(data + " loan amount entered sucessfully : " + amountKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void interestRateByText(String rateKey, String data) {
		try {
		 driver.findElement(By.xpath(prop.getProperty(rateKey))).sendKeys(data);
		 reportPass(data + " interest rate entered sucessfully : " + rateKey);
		 
	} catch (Exception e) {
		reportFail(e.getMessage());
		e.printStackTrace();
	}
}
		
	public void loanTermByText(String termKey,String data) {
		try {
		  driver.findElement(By.xpath(prop.getProperty(termKey))).sendKeys(data);
		  reportPass(data + " loan term entered sucessfully : " + termKey);
		  driver.findElement(By.xpath(prop.getProperty(termKey))).sendKeys(Keys.ENTER);
		  reportPass(" data entered sucessfully" );
	}catch (Exception e) {
		reportFail(e.getMessage());
		e.printStackTrace();
	}
}
	
	public void displayCarLoanEMI(String firstYearKey, String firstMonthPrincipalAmt, String firstMonthInterestAmt, String noOfYears) throws Exception {
		try {
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,1600)"); 
		 Thread.sleep(2500);
		 driver.findElement(By.xpath(prop.getProperty(firstYearKey))).click();
		 Thread.sleep(3000);
		 String principalAmount = driver.findElement(By.xpath(prop.getProperty(firstMonthPrincipalAmt))).getText();
		  System.out.println("First month Principal Amount for Car Loan: "+ principalAmount);
		  reportPass("First month Principal Amount for Car Loan: "+ principalAmount);
		   String interestAmount = driver.findElement(By.xpath(prop.getProperty(firstMonthInterestAmt))).getText();
		 System.out.println("First month Interest Amount for Car Loan: "+ interestAmount); 
		 reportPass ("First month Interest Amount for Car Loan: "+ interestAmount); 
		 utility.WriteCarLoanDetailsToExcel.writeCarLoanDetailsToExcel(workbookForCarLoan, carLoanTestCount, principalAmount, interestAmount);
		 Thread.sleep(3000);
		 reportPass("Excel sheet for car loan is created");
		 }catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	
	public void displayHomeLoanEMI(String noOfYears) throws Exception {
		try {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1760)"); 
		
		Calendar currentDate = Calendar.getInstance();
		int no_of_years = (int)Float.parseFloat(noOfYears);
		int trCount=2;
	    int monthNumber = currentDate.get(Calendar.MONTH) + 1;
	    if(monthNumber != 1) {
	    	no_of_years +=1;
	    }
	    String[] year = new String[no_of_years];
	    String[] principalAmount = new String[no_of_years]; 
	    String[] interestAmount = new String[no_of_years]; 
	    String[] totalPayment = new String[no_of_years]; 
	    String[] balance = new String[no_of_years]; 
	    String[] loanPaidToDate = new String[no_of_years]; 
	    for(int i = 0; i < no_of_years; i++) {
	    	
	    	String yearKeyLocator = utility.GetXpath.getXpath(trCount, 1);
	    	String principalAmountKeyLocator = utility.GetXpath.getXpath(trCount, 2);
	    	String interestAmountKeyLocator = utility.GetXpath.getXpath(trCount, 3);
	    	String totalPaymentKeyLocator = utility.GetXpath.getXpath(trCount, 4);
	    	String balanceKeyLocator = utility.GetXpath.getXpath(trCount, 5);
	    	String loanPaidToDateKeyLocator = utility.GetXpath.getXpath(trCount, 6);
	    	
	    	trCount += 2; //Increase Row number
	    	
	    	year[i] = driver.findElement(By.xpath(yearKeyLocator)).getText();
	    	//System.out.println("Year: "+year[i]);
	    	principalAmount[i] = driver.findElement(By.xpath(principalAmountKeyLocator)).getText();
	    	//System.out.println("Principal: "+principalAmount[i]);
	    	interestAmount[i] = driver.findElement(By.xpath(interestAmountKeyLocator)).getText();
	    	//System.out.println("Interest: "+interestAmount[i]);
	    	totalPayment[i] = driver.findElement(By.xpath(totalPaymentKeyLocator)).getText();
	    	//System.out.println("Total Payment: "+totalPayment[i]);
	    	balance[i] = driver.findElement(By.xpath(balanceKeyLocator)).getText();
	    	//System.out.println("Balance: "+balance[i]);
	    	loanPaidToDate[i] = driver.findElement(By.xpath(loanPaidToDateKeyLocator)).getText();
	    	//System.out.println("Loan Paid To Date: "+loanPaidToDate[i]);
		
	    }
	    
	    utility.WriteHomeLoanDetailsToExcel.writeHomeLoanDetailsToExcel(workbookForHomeLoan, homeLoanTestCount, year, principalAmount, interestAmount, totalPayment, balance, loanPaidToDate);
		Thread.sleep(3000);
		reportPass("Excel sheet for home loan is created");
		 }catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	
	
		
	public void tearDown() {
		driver.quit();
	}
	
	public void quitBrowser() {
		driver.quit();
	}
	//Taking the screenshot on failure 
		public void takeScreenShotOnFailure() {
			TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
			File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

			File destFile = new File(System.getProperty("user.dir") + "//screenshots//" + DateUtil.getTimeStamp() + ".png");
			try {
				FileUtils.copyFile(sourceFile, destFile);
				logger.addScreenCaptureFromPath(
						System.getProperty("user.dir") + "//screenshots//" + DateUtil.getTimeStamp() + ".png");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void reportFail(String reportString) {
			logger.log(Status.FAIL, reportString);
			takeScreenShotOnFailure();
			Assert.fail(reportString);
			
		}

		public void reportPass(String reportString) {
			logger.log(Status.PASS, reportString);
			
		}


		}


