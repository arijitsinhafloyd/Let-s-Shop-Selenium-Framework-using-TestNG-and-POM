package arijit.practice.pagecomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arijit.practice.abstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver){
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	private WebElement email;
	
	@FindBy(id="userPassword")
	private WebElement password;
	
	@FindBy(id="login")
	private WebElement loginButton;
	
	@FindBy(xpath="//div[contains(@class,'toast-error')]/div")
	private WebElement errorToast;
	
	
	public ProductCatalogue loginFlow(String email1, String password1) {
		email.sendKeys(email1);
		password.sendKeys(password1);
		clicking(loginButton);
		return new ProductCatalogue(driver);
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");
	}
	
	public String loginError() {
		waitElement(errorToast);
		String toastM=errorToast.getText().trim();
		return toastM;
	}
	
	
}
