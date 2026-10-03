package arijit.practice.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import arijit.practice.pagecomponents.LandingPage;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class BaseTest {
	
	public ThreadLocal<LandingPage> lp=new ThreadLocal<LandingPage>();
	public ThreadLocal<WebDriver> threadDriver=new ThreadLocal<WebDriver>();
	
	public WebDriver getDriver() {
		return threadDriver.get();
	}

	public WebDriver initializeDriver() throws IOException {
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+
				"\\src\\main\\java\\arijit\\practice\\resources\\GlobalProperties.properties");
		prop.load(fis);
		String browserName=System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
		
		WebDriver driver;
		
		if(browserName.contains("chrome")) {
			ChromeOptions options=new ChromeOptions();
			if(browserName.contains("headless")) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--disable-gpu");
			}
			 driver=new ChromeDriver(options);
		}
		else if(browserName.contains("firefox"))
			 driver=new FirefoxDriver();
		else
			driver=new EdgeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		
		threadDriver.set(driver);
		return driver;
		
	}
	
	public List<HashMap<String,String>> getJsonData(String filePath) throws IOException {
		String jSonContent=FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jSonContent, new TypeReference<List<HashMap<String,String>>>(){});
		return data;
	}
	
	public String screenshotImplementation(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String filePath=System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
		File file=new File(filePath);
		FileUtils.copyFile(src, file);
		return filePath; 
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage invokeBrowser() throws IOException {
		WebDriver driver=initializeDriver();
		LandingPage landingPage=new LandingPage(driver);
		lp.set(landingPage);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		
		WebDriver driver=getDriver();
		if(driver!=null) {
		driver.quit();
		threadDriver.remove();
		}
	}
	
}
