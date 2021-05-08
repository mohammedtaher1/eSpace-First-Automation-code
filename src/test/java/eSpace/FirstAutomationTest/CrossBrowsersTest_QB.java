package eSpace.FirstAutomationTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.read.biff.BiffException;


public class CrossBrowsersTest_QB {

	WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void StartingDriver(String browser) throws Exception {

		if(browser.equalsIgnoreCase("Chrome"))
		{
			String ChromePath = System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", ChromePath);
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("Firefox"))
		{
			String FirefoxPath = System.getProperty("user.dir") + "\\resources\\geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", FirefoxPath);
			DesiredCapabilities compabit = DesiredCapabilities.firefox();
			compabit.setCapability("marionette", true);
			driver = new FirefoxDriver();
		}
		else
		{
			throw new Exception("Browser is not correct");
		}
		
		driver.manage().window().maximize();
		driver.navigate().to("https://questionbank.espace.ws/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void loginTest() throws InterruptedException, BiffException, IOException
	{
		File excel_source = new File(System.getProperty("user.dir") + "\\Book1.xls");
		Workbook WB = Workbook.getWorkbook(excel_source);
		
		int LoopIndex;
		String ExpectedURL = "https://questionbank.espace.ws/me/my_questions";
		
		WebDriverWait wait = new WebDriverWait(driver,5);
		
		for(LoopIndex = 1; LoopIndex < WB.getSheet(0).getRows(); LoopIndex++)
		{
			
			WebElement Email = driver.findElement(By.name("email"));
			WebElement Password = driver.findElement(By.name("password"));
			WebElement LoginBTN = driver.findElement(By.cssSelector("button.submitBtn"));
			
			wait.until(ExpectedConditions.elementToBeClickable(LoginBTN));
			
			Email.clear();
			Email.sendKeys(WB.getSheet(0).getCell(0, LoopIndex).getContents());
			
			Password.clear();
			Password.sendKeys(WB.getSheet(0).getCell(1, LoopIndex).getContents());
			
			LoginBTN.click();
			
			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
			String ActualURL = driver.getCurrentUrl();
			
			if(ActualURL.equalsIgnoreCase(ExpectedURL))
			{
				System.out.println("Dataset " + LoopIndex + " is Valid");
				driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
				WebElement LogoutBTN = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/a/svg"));
				LogoutBTN.click();
				
			}
			else
			{
				System.out.println("Dataset " + LoopIndex + " is Invalid");
			}
			
		}

	}
	
	@AfterTest
	public void ClosingDriver()
	{
		driver.quit();
	}
	

}
