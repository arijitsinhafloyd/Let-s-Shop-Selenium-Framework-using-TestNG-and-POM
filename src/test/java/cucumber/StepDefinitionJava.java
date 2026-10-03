package cucumber;

import java.io.IOException;

import org.testng.Assert;

import arijit.practice.pagecomponents.CartPage;
import arijit.practice.pagecomponents.LastPage;
import arijit.practice.pagecomponents.OrdersPage;
import arijit.practice.pagecomponents.PreOrderPage;
import arijit.practice.pagecomponents.ProductCatalogue;
import arijit.practice.testComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionJava extends BaseTest{
	public ProductCatalogue pc;
	public PreOrderPage pop;
	public LastPage lop;
	public OrdersPage op;
	public CartPage cp;
	
	@Given("I landed on Landing Page")
	public void I_landed_on_Landing_Page() throws IOException {
		invokeBrowser();
		
	}
	
	@Given("^Logged in with (.+) and (.+)$")
	public void logged_in_with_email_and_password(String email, String password) {
		pc=lp.get().loginFlow(email,password);
		String loginToast=pc.loginToast();
		Assert.assertEquals(loginToast, "Login Successfully");
	}
	
	@When("Logging in with invalid credentials {string} and {string}")
	public void logged_in_with_invalid_crednetials(String email, String password) {
		pc=lp.get().loginFlow(email,password);
	}
	
	@When("^I add (.+) in cart$")
	public void I_add_product_in_cart(String product) {
		pc.productSelection(product);
		String addToast=pc.productCartToast();
		Assert.assertEquals(addToast, "Product Added To Cart");
	}
	
	@And("^checkout (.+) from Cart$")
	public void checkout_product_from_Cart(String product) {
		cp=pc.goingCart();
		
		//Cart section Verification
		boolean productFound=cp.productsInCartVerification(product);
		Assert.assertTrue(productFound,"Not found in cart");
		
		//Going to CheckOut
		pop=cp.checkOut();
	}
	
	@And("^fill (.+) details from Pre Order page and place order$")
	public void fill_country_details_from_Pre_Order_page_and_place_order(String country) {
		pop.orderDetails(country);
		lop=pop.orderPlacing();
	}
	
	@Then("Order will be placed")
	public void Order_will_be_placed() {
		boolean status=lop.orderMessage();
    	Assert.assertTrue(status,"Order unsuccessful");
	}
	
	@When("I landed on Orders Page")
	public void  I_landed_on_Orders_Page() {
		op=lp.get().goToOrder();
	}
	
	@Then("^we will find our (.+) there$")
	public void we_will_find_our_product_there(String product) {
		String prodNameActual=op.orderVerification();
		Assert.assertEquals(prodNameActual, product);
	}
	
	@Then("received a toast message {string}")
	public void received_a_toast_message(String string) {
		String toastM=lp.get().loginError();
		Assert.assertEquals(toastM, string);
	}
	
	@And("go to cart Page")
	public void go_to_cart_Page_and_search_for_another_product() {
		cp=pc.goingCart();
	}
	
	@Then("^product will not be found if you search for (.+)$")
	public void product_will_not_be_found_if_you_search_for_another_product(String product2) {
		boolean productFound=cp.productsInCartVerification(product2);
		Assert.assertFalse(productFound);
	}
	
	
	@After
	public void close() {
		tearDown();
	}
}
