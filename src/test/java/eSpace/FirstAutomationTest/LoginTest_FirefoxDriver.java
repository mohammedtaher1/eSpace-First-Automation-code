package eSpace.FirstAutomationTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.read.biff.BiffException;


public class LoginTest_FirefoxDriver {

	FirefoxDriver driver;
    
	//Set Configurations for starting the WebDriver
	@BeforeTest
	public void StartingDriver() {

		String FirefoxPath = System.getProperty("user.dir") + "\\resources\\geckodriver.exe";
		//Adding Chrome Driver executable file in the system properties
		System.setProperty("webdriver.gecko.driver", FirefoxPath);
		//Take new object from chrome driver
		driver = new FirefoxDriver();
		//Maximize window and got to the desired website
		driver.manage().window().maximize();
		driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");
	}

	//A Method created for login feature using data from excel sheet
	@Test
	public void loginTest() throws InterruptedException, BiffException, IOException
	{
		//Getting Source file location
		File excel_source = new File(System.getProperty("user.dir") + "\\Book1.xls");
		//Loading Workbook
		Workbook WB = Workbook.getWorkbook(excel_source);
		
		//Loop Counter used to get data used in login from the source file
		int LoopIndex;
		//Account Dashboard link
		String ExpectedURL = "http://automationpractice.com/index.php?controller=my-account";
		
		//Defining Explicit Wait
		WebDriverWait wait = new WebDriverWait(driver,5);
		
		for(LoopIndex = 1; LoopIndex < WB.getSheet(0).getRows(); LoopIndex++)
		{
			wait.until(ExpectedConditions.titleContains("Login"));
			
			//Finding Elements for Login Credentials
			WebElement Email = driver.findElement(By.id("email"));
			WebElement Password = driver.findElement(By.id("passwd"));
			WebElement LoginBTN = driver.findElement(By.id("SubmitLogin"));
			
			//Setting Email and Password in the required fields
			Email.clear();
			Email.sendKeys(WB.getSheet(0).getCell(0, LoopIndex).getContents());
			
			Password.clear();
			Password.sendKeys(WB.getSheet(0).getCell(1, LoopIndex).getContents());
			
			//Click on login button
			LoginBTN.click();
			
			//Implicit wait
			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
			String ActualURL = driver.getCurrentUrl();
			
			//Checking if the browser navigated to the Account Dashboard OR not
			if(ActualURL.equalsIgnoreCase(ExpectedURL))
			{
				System.out.println("Dataset " + LoopIndex + " is Valid");
				driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
				//Find Logout button
				WebElement LogoutBTN = driver.findElement(By.className("logout"));
				LogoutBTN.click();
			}
			else
			{
				System.out.println("Dataset " + LoopIndex + " is Invalid");
			}
			
		}

	}
	
	//Closing The Driver
	@AfterTest
	public void ClosingDriver()
	{
		driver.quit();
	}
	

}
