package arijit.practice.pagecomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import arijit.practice.abstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver){
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "#toast-container div div")
	private WebElement toast1;
	
	private By toast2= By.cssSelector(".toast-container");
	
	@FindBy(css=".mb-3")
	private List<WebElement> products;
	
	private By productsList=By.cssSelector(".mb-3");
	private By animation=By.cssSelector(".ng-animating");
	private By toastContainer=By.cssSelector(".toast-container");
	private By addToCartButton=By.xpath(".//button[starts-with(text(),' Add To')]");
	
	public String loginToast() {
		waitElement(toast1);
		String loginToast=toast1.getText().trim();
		invisibilityElement(toastContainer);
		return loginToast;
	}
	
	public List<WebElement> getProducts() {
		waitBy(productsList);
		return products;
	}
	
	public void productSelection(String prodName) {
		getProducts();
		for(int i=0;i<products.size();i++) {
        	String product=products.get(i).findElement(By.cssSelector("div h5 b")).getText().trim();
        	if(product.equals(prodName)) {
        		WebElement addToCart = products.get(i).findElement(addToCartButton);
        		clicking(addToCart);
        		break;
        	}
        }
	}
	
	public String productCartToast() {
		WebElement toastM=waitBySingleElement(toast2);
		String addToast=toastM.getText().trim();
		invisibilityElement(toast2);
		invisibilityElement(animation);
		return addToast;
	}
}
