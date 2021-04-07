package comp.dbs.cards;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sg.dbs.base.BasePage;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class CompareCreditCardsTest {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().fullscreen();
		driver.get("https://www.dbs.com.sg/index/default.page");
		wait= new WebDriverWait(driver,10);
		
	}
	
	@Test
	public void compareCreditCardsTest() {
		
		
//		Creditcards is under Personal Banking > DBS.
		driver.findElement(By.linkText("Personal Banking")).click();
		
//		Under Personal Banking, click on DBS which is the first link.
		List<WebElement> bankLinks = driver.findElements(By.cssSelector(".title-head > .tile-content-headline"));
		bankLinks.get(0).click();
		
		driver.findElement(By.linkText("Cards")).click();
		driver.findElement(By.linkText("Credit Cards")).click();
		
//		There is a delay in loading list of credit cards.
		wait.until(presenceOfElementLocated(By.cssSelector(".cardContainer")));

		
		
//		Scroll down
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,550)");
		
//		Select credit cards to be compared with their names.
//		DBS Altitude Visa Signature Card
//		DBS Black Visa Card
		
		WebElement VisaSignatureCard = driver.findElement(By.xpath("//div[contains(text(), \"DBS Altitude Visa Signature Card\")]"));
		VisaSignatureCard.click();
		WebElement VisaBlackCard=driver.findElement(By.xpath("//div[contains(text(), \"DBS Black Visa Card\")]"));
		VisaBlackCard.click();
		
		driver.findElement(By.xpath("//button[contains(text(), \"Compare\")]")).click();
		
		List<WebElement> VisaSignatureCardDetails = driver.findElements(By.cssSelector("#card0 > .section-seperator > .sub-header"));
		List<WebElement> VisaBlackCardDetails = driver.findElements(By.cssSelector("#card1 > .section-seperator > .sub-header"));
		
		
//		Assert the card details for both the cards selected to compare.
		
		assertEquals(VisaSignatureCardDetails.get(0).getText(),"It's the fastest way to fly anywhere.","Best For");
		assertEquals(VisaBlackCardDetails.get(0).getText(),"Shopping is the new black","Best For");
		
		assertEquals(VisaSignatureCardDetails.get(1).getText(),"VISA","Card Type");
		assertEquals(VisaBlackCardDetails.get(1).getText(),"VISA","Card Type");
		
		assertEquals(VisaSignatureCardDetails.get(2).getText(),"S$30,000","Min Income Per Annum");
		assertEquals(VisaBlackCardDetails.get(2).getText(),"S$30,000","Min Income Per Annum");
		
		assertEquals(VisaSignatureCardDetails.get(3).getText(),"S$45,000","Min Income Per Annum Foreigners");
		assertEquals(VisaBlackCardDetails.get(3).getText(),"S$45,000","Min Income Per Annum Foreigners");
		
		assertEquals(VisaSignatureCardDetails.get(5).getText(),"1 Year","Annual Fee Waiver");
		assertEquals(VisaBlackCardDetails.get(5).getText(),"1 Year","Annual Fee Waiver");
				
	}
	@AfterMethod
	public void closeTest() {
		driver.quit();
	}

}
	
