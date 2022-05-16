package demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.deque.html.axecore.axeargs.AxeRunOnlyOptions;
import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment {
	
	WebDriver driver;
	
	
	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://dequeuniversity.com/demo/mars");
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	
	@Test
	public void tc001() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav[@id='main-nav']")));
		Assert.assertTrue(driver.findElements(By.xpath("//nav[@id='main-nav']")).size()>0);
		Assert.assertEquals(driver.findElements(By.xpath("//div[@id='widget-controls']//input")).size(), 5);
		int iniitialSize = driver.findElements(By.xpath("//div[@class='dynamic']//span[contains(text(),'Traveler')]")).size();
		driver.findElement(By.xpath("//div[@id='add-traveler']//a")).click();
		int sizeAfterAdding = driver.findElements(By.xpath("//div[@class='dynamic']//span[contains(text(),'Traveler')]")).size();
		Assert.assertTrue(sizeAfterAdding==iniitialSize+1);
		
		String videoTitle_Before = driver.findElement(By.xpath("//div[@id='video-box']//*[@id='video-text']")).getText();
		driver.findElement(By.xpath("//div[@id='video-box']//i[contains(@class,'vid-next')]")).click();
		String videoTitle_After = driver.findElement(By.xpath("//div[@id='video-box']//*[@id='video-text']")).getText();
		Assert.assertNotEquals(videoTitle_Before, videoTitle_After);
	}

	@Test
	public void tc002() {
	
		AxeRunOnlyOptions runOnlyOptions = new AxeRunOnlyOptions();
	    runOnlyOptions.setType("tag");
	    runOnlyOptions.setValues(Arrays.asList("wcag2a", "wcag2aa", "wcag2aaa", "wcag21a", "wcag21aa", "wcag21aaa"));
	    
	    AxeRunOptions options = new AxeRunOptions();
	    options.setRunOnly(runOnlyOptions);
	    
	    AxeBuilder axe = new AxeBuilder().withOptions(options);
	    Results result = axe.analyze(driver);
	    result.getViolations();
	    
	    List<Rule> violationList = result.getViolations();
	    
	    for (Rule rule : violationList) {
			System.out.println(rule);
		}
	    
	    Assert.assertEquals(violationList.size(), 11);
	    
	}
}
