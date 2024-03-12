package com.herokuapp.theinternet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class PositiveTests {
	private Logger log = Logger.getLogger(PositiveTests.class);
	
	@Test
	public void loginTest() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		log.info("Driver Instatiated");
		
		String url ="https://the-internet.herokuapp.com/login";
		driver.get(url);
		log.info("Opened the url");
		
		driver.manage().window().maximize();
		log.info("Maximized the driver window");
		
		driver.quit();
		log.info("Quit browser");
	}

}
