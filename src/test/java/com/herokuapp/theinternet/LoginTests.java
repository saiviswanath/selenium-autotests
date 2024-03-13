package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class LoginTests {
	private Logger log = Logger.getLogger(LoginTests.class);
	
	private WebDriver driver;
	
	@Parameters({"browser"})
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default:
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		
		log.info("Driver Instatiated");
		
		driver.manage().window().maximize();
		log.info("Maximized the driver window");
	}
	
	@Parameters({"uname", "pwd"})
	@Test(priority = 1, groups = {"positiveTests", "smokeTests"})
	public void loginSuccessTest(String uname, String pwd) {
		
		String url ="https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Opened the url");
		
		WebElement unameEle = driver.findElement(By.id("username"));
		unameEle.sendKeys(uname);
		
		WebElement pwdEle = driver.findElement(By.name("password"));
		pwdEle.sendKeys(pwd);
		
		WebElement loginbutEle = driver.findElement(By.tagName("button"));
		loginbutEle.click();
		
		sleep(3000); //Optional only to check if test is happening.
		
		//Verifications
		
		String expectedUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Expected and actual urls are not same");
		
		WebElement logoutbuttonEle = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logoutbuttonEle.isDisplayed(), "Login Button is not displayed");
		
		WebElement successMsg = driver.findElement(By.cssSelector("#flash"));
		String expectedMessage = "You logged into a secure area!";
		String actualMsg = successMsg.getText();
		//Assert.assertEquals(actualMsg, expectedMessage, "Actual message is not the same as expected");
		Assert.assertTrue(actualMsg.contains(expectedMessage), "Actual message does not contain expected message.\nActual Message: " + actualMsg
				+ "\nExpected Message: " + expectedMessage);
	}
	
	@Parameters({"uname", "pwd", "expectedMsg"})
	@Test(priority = 2, groups = {"negativeTests", "smokeTests"})
	public void loginFailedTests(String uname, String pwd, String expectedMsg) {
		
		String url ="https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Opened the url");
		
		WebElement unameEle = driver.findElement(By.id("username"));
		unameEle.sendKeys(uname);
		
		WebElement pwdEle = driver.findElement(By.name("password"));
		pwdEle.sendKeys(pwd);
		
		WebElement loginbutEle = driver.findElement(By.tagName("button"));
		loginbutEle.click();
		
		sleep(3000); //Optional only to check if test is happening.
		
		// Verifications
		WebElement errorMsg = driver.findElement(By.xpath("//div[@id='flash']"));
		String actualMsg = errorMsg.getText();
		Assert.assertTrue(actualMsg.contains(expectedMsg), "Actual message does not contain expected message.\nActual Message: " + actualMsg
				+ "\nExpected Message: " + expectedMsg);
		
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		driver.quit();
		log.info("Quit browser");
	}
	
	private void sleep(long millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
