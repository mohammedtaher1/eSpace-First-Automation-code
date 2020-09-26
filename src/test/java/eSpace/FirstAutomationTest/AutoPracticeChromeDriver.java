package eSpace.FirstAutomationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutoPracticeChromeDriver {
	
	ChromeDriver driver;
	
	@BeforeTest
	public void StartingDriver() {
		
		String ChromePath = System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", ChromePath);
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");
	}
	
	@Test
	public void Add_new_email() throws InterruptedException
	{
		WebElement email_field = driver.findElement(By.id("email_create"));
		WebElement Create_Btn = driver.findElement(By.id("SubmitCreate"));
		
		email_field.clear();
		email_field.sendKeys("myemail4@hotmail.com");
		Create_Btn.click();
		
		Thread.sleep(3000);
		
		String ExpectedURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation";
		String Actual = driver.getCurrentUrl();
		
		Assert.assertEquals(Actual,ExpectedURL);
		
	}
	
	@Test(dependsOnMethods = {"Add_new_email"})
	public void Fill_Registration_Form() throws InterruptedException
	{
		WebElement Title = driver.findElement(By.xpath("//*[@id=\"id_gender1\"]"));
		Title.click();
		
		WebElement fName = driver.findElement(By.id("customer_firstname"));
		fName.clear();
		fName.sendKeys("Mohammed");
		
		WebElement LName = driver.findElement(By.id("customer_lastname"));
		LName.clear();
		LName.sendKeys("Taher");
		
		WebElement Password = driver.findElement(By.name("passwd"));
		Password.clear();
		Password.sendKeys("mohamedmt10295");
		
		WebElement Address_fName = driver.findElement(By.name("firstname"));
		Address_fName.clear();
		Address_fName.sendKeys("address first name");
		
		WebElement Address_LName = driver.findElement(By.cssSelector("input#lastname.form-control"));
		Address_LName.clear();
		Address_LName.sendKeys("address last name");
		
		WebElement Address = driver.findElement(By.xpath("//*[@id=\"address1\"]"));
		Address.clear();
		Address.sendKeys("address");
		
		WebElement city = driver.findElement(By.id("city"));
		city.clear();
		city.sendKeys("Mycity");
		
		WebElement state_drop_down = driver.findElement(By.id("id_state"));
		Select dropdown = new Select(state_drop_down);
		dropdown.selectByIndex(3);
		
		WebElement postalCode = driver.findElement(By.id("postcode"));
		postalCode.clear();
		postalCode.sendKeys("00002");
		
		WebElement Mobile_Number = driver.findElement(By.name("phone_mobile"));
		Mobile_Number.clear();
		Mobile_Number.sendKeys("00201010000000");
		
		WebElement aliasAddress= driver.findElement(By.id("alias"));
		aliasAddress.clear();
		aliasAddress.sendKeys("my alias address");
		
		WebElement SubmitBTN = driver.findElement(By.id("submitAccount"));
		SubmitBTN.click();
		
		Thread.sleep(3000);
		
		String NextURL = "http://automationpractice.com/index.php?controller=my-account";
		String ActualURL = driver.getCurrentUrl();
		
		Assert.assertEquals(ActualURL, NextURL);
		
	}
	
	@AfterTest
	public void ClosingDriver()
	{
		driver.quit();
	}

}
