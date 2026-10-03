package arijit.practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import arijit.practice.pagecomponents.CartPage;
import arijit.practice.pagecomponents.LastPage;
import arijit.practice.pagecomponents.OrdersPage;
import arijit.practice.pagecomponents.PreOrderPage;
import arijit.practice.pagecomponents.ProductCatalogue;
import arijit.practice.testComponents.BaseTest;
import arijit.practice.testComponents.Retry;

public class PositiveTests extends BaseTest{

	@Test(dataProvider="getData", retryAnalyzer=Retry.class)
	public void SubmitOrderTest(HashMap<String,String> input) throws IOException {
		// TODO Auto-generated method stub

		ProductCatalogue pc=lp.get().loginFlow(input.get("email"),input.get("password"));
		String loginToast=pc.loginToast();
		Assert.assertEquals(loginToast, "Login Successfully");
		
		//Products adding to Cart
		pc.productSelection(input.get("product"));
		String addToast=pc.productCartToast();
		Assert.assertEquals(addToast, "Product Added To Cart");
		
		//Add to Cart button
		CartPage cp=pc.goingCart();
		
		//Cart section Verification
		boolean productFound=cp.productsInCartVerification(input.get("product"));
		Assert.assertTrue(productFound,"Not found in cart");
		
		//Going to CheckOut
		PreOrderPage pop=cp.checkOut();
		
		//PreOrderInfoPage
		pop.orderDetails(input.get("country"));
		LastPage lop=pop.orderPlacing();
		
		//Last Page 
		boolean status=lop.orderMessage();
    	Assert.assertTrue(status,"Order unsuccessful");
		
	}
	
	@Test(dependsOnMethods= {"SubmitOrderTest"},dataProvider="getData", retryAnalyzer=Retry.class)
	public void productVerification(HashMap<String,String> input) {
		
		ProductCatalogue pc=lp.get().loginFlow(input.get("email"),input.get("password"));
		String loginToast=pc.loginToast();
		Assert.assertEquals(loginToast, "Login Successfully");
		
		OrdersPage op=lp.get().goToOrder();
		String prodNameActual=op.orderVerification();
		Assert.assertEquals(prodNameActual, input.get("product"));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data=getJsonData(System.getProperty("user.dir")+
				"\\src\\main\\java\\arijit\\practice\\resources\\DataForTests.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
