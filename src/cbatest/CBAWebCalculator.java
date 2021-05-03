package cbatest;

import java.io.File;

//import javax.lang.model.element.Element;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;

//import NgTest.Selenium.FirstKickSteps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;

public class CBAWebCalculator {

	private static WebDriver myDriver=null;
	private static boolean b_homePageLoaded=false;
	enum Repayment_Type
	{
		P_ND_I,IO_1,IO_2,IO_3,IO_4,IO_5
	};
	
	enum Loan_Type
	{
		OO_30PCT,OO_20PCT,OO_10PCT,OO_SVR,OO_1Y_FIX,OO_2Y_FIX,OO_3Y_FIX,OO_4Y_FIX,OO_5Y_FIX,
		IV_30PCT,IV_20PCT,IV_10PCT,IV_SVR,IV_1Y_FIX,IV_2Y_FIX,IV_3Y_FIX,IV_4Y_FIX,IV_5Y_FIX
	};
	
	public void prepared_test_initialization() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe"); 
		if (null==myDriver) myDriver=(WebDriver) new ChromeDriver();
		//new Chromedriver
		//System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox/firefox.exe"); 
	    //throw new PendingException();
		//myDriver.
	}
	
	public void start_browser() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		//myDriver.manage().window().maximize();
		myDriver.manage().window().setPosition(new Point(40,60));
	   // throw new PendingException();
	}
	
	public void close_brower() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		//Assert.assertTrue(FirstKickSteps.b_homePageLoaded);
		myDriver.close();
	    //throw new PendingException();
	}
	public WebElement go_to_CBA_home_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		myDriver.get("https://www.commbank.com.au");
		WebDriverWait wait = new WebDriverWait(myDriver, 8);
		//myDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//WebElement element = wait.until(myDriver.findElement(By.id("")));
		//Thread.sleep(5000);
		//myDriver
	    //throw new PendingException();
		WebElement element =wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Repayments calculator")));
		//WebElement homeObj=myDriver.findElement(By.id("country-dropdown"));
	
		System.out.println(" Element: "+ element.getText());
		CBAWebCalculator.b_homePageLoaded=(element.getText()!=null);
		takeScreenShot("./cba_homepage.jpg");
		return element;
		
	}
	
	public void takeScreenShot (String dstFile)  throws Throwable
	{
		TakesScreenshot scrShot =((TakesScreenshot)myDriver);
		File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
		 File DestFile=new File(dstFile);

         //Copy file at destination

         FileUtils.copyFile(srcFile, DestFile);
	}
	
	public void go_to_CBA_CalculatorPage(WebElement we) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		Actions builder = new Actions(myDriver); 
	    builder.moveToElement(we).build().perform();
	    
	    WebDriverWait wait = new WebDriverWait(myDriver, 8);
		//myDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//WebElement element = wait.until(myDriver.findElement(By.id("")));
		we.click();
		//myDriver
	    //throw new PendingException();
		WebElement element =wait.until(ExpectedConditions.elementToBeClickable(By.id("term")));
		//WebElement homeObj=myDriver.findElement(By.id("country-dropdown"));
		System.out.println(" Element: "+ element.getText());
		takeScreenShot("./calculator_page.jpg");
		Thread.sleep(5000);
	}
	public void back_to_calculator()
	{
		WebDriverWait wait = new WebDriverWait(myDriver, 1);
		WebElement element =myDriver.findElement(By.id("repayments-link"));
		if(element.isEnabled())
		{
			element.click();
			WebElement iElement =wait.until(ExpectedConditions.elementToBeClickable(By.id("interestOnly")));
		}
		
		WebElement editElement =myDriver.findElement(By.cssSelector("a[aria-controls='repayments-question-form']"));
		if(!("active".equalsIgnoreCase(editElement.getAttribute("class"))))
		{
			
			//editElement.click();
			JavascriptExecutor jsExecutor=(JavascriptExecutor)(myDriver);
			jsExecutor.executeScript("arguments[0].click();", editElement);
			WebElement iElement =wait.until(ExpectedConditions.elementToBeClickable(By.id("interestOnly")));
			
		}
	}
	public void calculate_repayment(int amount, int term, Repayment_Type paytype, Loan_Type OO_IV_choice) throws Throwable {
	
		WebElement amtElement=myDriver.findElement(By.id("amount"));
		amtElement.clear();
		amtElement.sendKeys(String.valueOf(amount));
		
		WebElement termElement=myDriver.findElement(By.id("term"));
		termElement.clear();
		termElement.sendKeys(String.valueOf(term));
		
		WebElement PIO_Element=myDriver.findElement(By.id("interestOnly"));
		//System.out.println(PIO_Element.getClass().toGenericString());
		System.out.println("it's: "+PIO_Element.getTagName());
		Select myDropDown=new Select(PIO_Element);
		myDropDown.selectByIndex(paytype.ordinal());
		//PIO_Element.selectByIndex(paytype.ordinal());
		
		Select  prdElement=new Select((myDriver.findElement(By.id("productId"))));
		prdElement.selectByIndex(paytype.ordinal());
		
		WebElement btCalculatElement=myDriver.findElement(By.id("submit"));
		try {

		    if(btCalculatElement.isEnabled())	btCalculatElement.click();
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		WebDriverWait wait = new WebDriverWait(myDriver, 4);
		//myDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);


		WebElement element =wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[data-tid='repayment-amount']")));
		
		WebElement repaymentElement=myDriver.findElement(By.cssSelector("span[data-tid='repayment-amount']"));
		
		System.out.println("Your monthly repayment:   ["+repaymentElement.getText()+"]");
		
		takeScreenShot("./calculator_"+amount+"_"+term+"_"+paytype+"_"+OO_IV_choice+".jpg");
		back_to_calculator();
		Thread.sleep(1500);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CBAWebCalculator ccCalculator=new CBAWebCalculator();
		try {
		
			ccCalculator.prepared_test_initialization();
			ccCalculator.start_browser();
			WebElement wbElement=ccCalculator.go_to_CBA_home_page();
			ccCalculator.go_to_CBA_CalculatorPage(wbElement);
			
			ccCalculator.calculate_repayment(200000, 30, Repayment_Type.IO_3,  Loan_Type.IV_4Y_FIX);
			
			ccCalculator.calculate_repayment(500000, 20, Repayment_Type.P_ND_I, Loan_Type.OO_20PCT);
			
			ccCalculator.calculate_repayment(350000, 15, Repayment_Type.IO_5, Loan_Type.IV_SVR);
			
			
			
		} catch ( Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				ccCalculator.close_brower();
				
			} catch ( Throwable e2) {
				// TODO: handle exception
			}
			
		}
	}

}
