package arijit.practice.pagecomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arijit.practice.abstractComponents.AbstractComponents;

public class LastPage extends AbstractComponents{
	
	WebDriver driver;

	public LastPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="td h1")
	private WebElement orderTextElement;
	
	public boolean orderMessage() {
		boolean status=false;
		waitElement(orderTextElement);
		String order=orderTextElement.getText().trim();
		if(order.equalsIgnoreCase("Thankyou for the order."))
			status=true;
		return status;
	}

}
