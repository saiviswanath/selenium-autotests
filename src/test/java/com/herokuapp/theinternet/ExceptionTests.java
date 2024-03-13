package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class ExceptionTests {
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
		
		//Implicit wait.
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@Test(enabled = false)
	public void noSuchElementExceptionTest() {
		
		String url ="https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		log.info("Opened the url");
		
		WebElement addBtnEle = driver.findElement(By.xpath("//button[@id='add_btn']"));
		addBtnEle.click();
		
		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement inputText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
		Assert.assertTrue(inputText.isDisplayed(), "Input text is not displayed");
	}
	
	@Test(enabled=false)
	public void elementNotInteractableTest() {
		
		String url ="https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		log.info("Opened the url");
		
		WebElement addBtnEle = driver.findElement(By.xpath("//button[@id='add_btn']"));
		addBtnEle.click();
		
		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement inputText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		inputText.sendKeys("Burger");
		
		WebElement saveButEle = driver.findElement(By.xpath("//div[@id='rows']/div[3]/div[@class='row']/button[@id='save_btn']"));
		saveButEle.click();
		
		WebElement confirmationText = driver.findElement(By.id("confirmation"));
		
		String actualMessage = confirmationText.getText();
		String expectedMsg = "Row 2 was saved";
		
		Assert.assertEquals(actualMessage, expectedMsg, "Message doesn't match");
	}
	
	@Test(enabled=false)
	public void invalieElementStateExceptionTest() {
		
		// Test case 3: InvalidElementStateException
		// Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		
		
		// Clear input field
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row1Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
		WebElement editButton = driver.findElement(By.id("edit_btn"));
		editButton.click();
		
		wait.until(ExpectedConditions.elementToBeClickable(row1Input));
		row1Input.clear();
				
		// Type text into the input field
		row1Input.sendKeys("Sushi");
		
		WebElement saveButton = driver.findElement(By.id("save_btn"));
		saveButton.click();
		
		// Verify text changed
		String value = row1Input.getAttribute("value");
		Assert.assertEquals(value, "Sushi", "Input 1 field value is not expected");
		
		// Verify text saved
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
		String messageText = confirmationMessage.getText();
		Assert.assertEquals(messageText, "Row 1 was saved", "Confirmation message text is not expected");
	}
	
	@Test(enabled=false)
	public void staleElementRefernceExceptionTest() {
		
		// Test case 4: StaleElementReferenceException
		// Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		
		// Push add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
		// Verify instruction text element is no longer displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "Instructions are still displayed");
	}
	
	@Test
	public void timeoutExceptionTest() {
		
		// Test case 5: TimeoutException
		// Open page
		driver.get("https://practicetestautomation.com/practice-test-exceptions/");
		
		// Click Add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
		// Wait for 3 seconds for the second input field to be displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
		// Verify second input field is displayed
		Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
	}
	
	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		driver.quit();
		log.info("Quit browser");
	}
}
