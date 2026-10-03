package arijit.practice.pagecomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arijit.practice.abstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents{
	
	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProductsList;
	
	private By cartProducts=By.cssSelector(".cartSection h3");
	
	@FindBy(xpath="//button[text()='Checkout']")
	private WebElement checkOutButton;
	
	public List<WebElement> getCartProducts() {
		waitBy(cartProducts);
		return cartProductsList;
	}
	
	public boolean productsInCartVerification(String prodName) {
		boolean productFound=false;
		getCartProducts();
		for(int i=0;i<cartProductsList.size();i++) {
    		String pro1=cartProductsList.get(i).getText().trim();
    		if(pro1.equalsIgnoreCase(prodName)) {
    			productFound=true;
    			break;
    		}
    	}
		return productFound;
	}
	
	public PreOrderPage checkOut() {
		clicking(checkOutButton);
		return new PreOrderPage(driver);
	}
	
}
