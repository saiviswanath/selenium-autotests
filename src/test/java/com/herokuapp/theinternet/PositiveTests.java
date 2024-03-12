package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class PositiveTests {
	private Logger log = Logger.getLogger(PositiveTests.class);
	
	@Test
	public void loginTest() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		log.info("Driver Instatiated");
		
		driver.manage().window().maximize();
		log.info("Maximized the driver window");
		
		String url ="https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Opened the url");
		
		WebElement unameEle = driver.findElement(By.id("username"));
		unameEle.sendKeys("tomsmith");
		
		WebElement pwdEle = driver.findElement(By.name("password"));
		pwdEle.sendKeys("SuperSecretPassword!");
		
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
