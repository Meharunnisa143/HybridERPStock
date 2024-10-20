package commonFuntions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	//method for launchin url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("url"));
	}
	//method for wait to any element
	public static void waitForElement(String LocatorType, String LocatorValue, String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//wait untill element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	//method for any textbox
	public static void typeAction(String LocatorType, String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		
	}
	//method for buttons,checkbox,radiobuttons,images,links
	public static void clickAction(String LocatorType, String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	//method for validate page title// here arguments can be our choice 
	public static void validateTitle(String Expected_Title)
	{
		String Actual_title = driver.getTitle();
	    try {
		Assert.assertEquals(Actual_title, Expected_Title,"Tiltle is not matching");
	    }catch(AssertionError a)
	    {
	    	System.out.println(a.getMessage());
	    }
	 
	}
	//method for close browser
	public static void closeBrowser()
	{
		driver.close();
	}
	//method for listboxes
    public static void dropDownAction(String LocatorType, String LocatorValue, String TestData)
    {
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		//convert testdata cell into int
    		int value = Integer.parseInt(TestData);
    		Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
    		element.selectByIndex(value);
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		//convert testdata cell into int
    		int value = Integer.parseInt(TestData);
    		Select element = new Select(driver.findElement(By.name(LocatorValue)));
    		element.selectByIndex(value);
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		//convert testdata cell into int
    		int value = Integer.parseInt(TestData);
    		Select element = new Select(driver.findElement(By.id(LocatorValue)));
    		element.selectByIndex(value);
    	}
    }
    //method capture stock number into notepad
    public static void captureStock(String LocatorType, String LocatorValue) throws Throwable
    {
    	String StockNumber ="";
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		StockNumber= driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		StockNumber= driver.findElement(By.name(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		StockNumber= driver.findElement(By.id(LocatorValue)).getAttribute("value");
    	}
    	//writing stock number into notepad
    	FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(StockNumber);
    	bw.flush();
    	bw.close();
    }
    //verify stock number in stock table
    public static void stockTable() throws Throwable
    {
    	//read path of notepad file
    	FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
    	BufferedReader br = new BufferedReader(fr);
    	String Exp_Data = br.readLine();
    	//click search panel if search tetbox not displayed
    	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
    		//click search panel
    		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
    	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
    	Thread.sleep(3000);
    	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
    	Reporter.log(Exp_Data+"       "+Act_Data,true);
    	try {
    		Assert.assertEquals(Exp_Data, Act_Data,"Stock number  not found in Table");
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
    	
    }
    //method for capture supplier number into notepad
    public static void capturesup(String LocatorType, String LocatorValue) throws Throwable
    {
    	String supplierNum ="";
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		supplierNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		supplierNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		supplierNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
    	}
    	FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(supplierNum);
    	bw.flush();
    	bw.close();
    }
    //method for reading supplier number and verifying supplier table
    public static void suppliertable() throws Throwable
    {
    	//read supplier number from above notepad
    	FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
    	BufferedReader br = new BufferedReader(fr);
    	String Exp_Data = br.readLine();
    	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
    		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
    	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
    	Thread.sleep(3000);
    	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
    	Reporter.log(Exp_Data+"      "+Act_Data,true);
    	try {
			Assert.assertEquals(Act_Data, Exp_Data, "Supplier number notfound in table");
		} catch (AssertionError a) {
			System.out.println(a.getLocalizedMessage());
		}
    }
    public static void capturecus(String LocatorType, String LocatorVlaue) throws Throwable
    {
    	String Customernum = "";
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		Customernum= driver.findElement(By.xpath(LocatorVlaue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		Customernum= driver.findElement(By.name(LocatorVlaue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		Customernum= driver.findElement(By.id(LocatorVlaue)).getAttribute("value");
    	}
    	FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(Customernum);
    	bw.flush();
    	bw.close();
    }
    public static void customertable() throws Throwable
    {
    	FileReader fr = new FileReader("./CaptureData/customernumber.txt");
    	BufferedReader br = new BufferedReader(fr);
    	String Exp_Data = br.readLine();
    	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
    		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
    	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
    	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
    	Thread.sleep(3000);
    	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
    	Reporter.log(Act_Data+"      "+Exp_Data,true);
    	try {
			Assert.assertEquals(Act_Data, Exp_Data, "customer number notfound in table");
		} catch (AssertionError e) {
			System.out.println(e.getLocalizedMessage());
		}
    }
}
