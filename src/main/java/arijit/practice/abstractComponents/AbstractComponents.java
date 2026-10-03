package arijit.practice.abstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import arijit.practice.pagecomponents.CartPage;
import arijit.practice.pagecomponents.OrdersPage;

public class AbstractComponents {
	
	WebDriver driver;
	
	public AbstractComponents(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	WebElement addToCartButton;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	WebElement orderButton;
	
	public void clicking(WebElement element) {
		JavascriptExecutor js=((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();",element);
	}
	
	public void waitElement(WebElement element) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void invisibilityElement(By findBy) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	public void waitBy(By findBy) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
	}
	
	public WebElement waitBySingleElement(By findBy) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
		return element;
	}
	
	public CartPage goingCart() {
		clicking(addToCartButton);
		return new CartPage(driver);
	}
	
	public void sendKeysOption(WebElement selectCountry, String input) {
		JavascriptExecutor js=((JavascriptExecutor) driver);
		js.executeScript("arguments[0].focus();" +
		        "arguments[0].value = arguments[1];" +

		        // input event (required)
		        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +

		        // keyboard events (THIS triggers dropdown logic)
		        "arguments[0].dispatchEvent(new KeyboardEvent('keydown', { bubbles: true, key: 'i' }));" +
		        "arguments[0].dispatchEvent(new KeyboardEvent('keyup', { bubbles: true, key: 'i' }));" +

		        // open dropdown explicitly (many libs listen to ArrowDown)
		        "arguments[0].dispatchEvent(new KeyboardEvent('keydown', { bubbles: true, key: 'ArrowDown' }));",
	       selectCountry, input);
	}
	
	public OrdersPage goToOrder() {
		clicking(orderButton);
		return new OrdersPage(driver);
	}
}
