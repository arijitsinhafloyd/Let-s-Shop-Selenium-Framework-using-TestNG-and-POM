package arijit.practice.pagecomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arijit.practice.abstractComponents.AbstractComponents;

public class PreOrderPage extends AbstractComponents{

	WebDriver driver;
	
	public PreOrderPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="input[placeholder='Select Country']")
	private WebElement selectCountry;
	
	@FindBy(css="div[class='form-group'] section button")
	private List<WebElement> countryDropdownOptions;
	
	@FindBy(xpath="//a[contains(text(),'Order')]")
	private WebElement orderButton;
	
	private By countryBy=By.cssSelector("div[class='form-group'] section button");
	
	public List<WebElement> getCountries() {
		waitBy(countryBy);
		return countryDropdownOptions;
	}
	
	public void orderDetails(String country) {
		clicking(selectCountry);
		sendKeysOption(selectCountry,"ind");
		getCountries();
		for(int i=0;i<countryDropdownOptions.size();i++) {
			String country1=countryDropdownOptions.get(i).getText().trim();
    		if(country1.equalsIgnoreCase(country)) {
    			clicking(countryDropdownOptions.get(i));
    			break;
    		}
    	}
	}
	
	public LastPage orderPlacing() {
		clicking(orderButton);
		return new LastPage(driver);
	}

}
