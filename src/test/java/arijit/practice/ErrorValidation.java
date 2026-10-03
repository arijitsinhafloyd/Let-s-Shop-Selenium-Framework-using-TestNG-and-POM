package arijit.practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import arijit.practice.pagecomponents.CartPage;
import arijit.practice.pagecomponents.ProductCatalogue;
import arijit.practice.testComponents.BaseTest;
import arijit.practice.testComponents.Retry;
import junit.framework.Assert;

public class ErrorValidation extends BaseTest{
	
	String prodName="ADIDAS ORIGINAL";
	
	@Test(groups= {"ErrorValidations"})
	public void loginError() {
		lp.get().loginFlow("mrekm@gmail.com", "Aser@3456");
		String toastM=lp.get().loginError();
		Assert.assertEquals(toastM, "Incorrect email or password.");
	}
	
	@Test(dataProvider="getData",groups= {"ErrorValidations"},retryAnalyzer=Retry.class)
	public void productError(HashMap<String,String> input) {
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
		boolean productFound=cp.productsInCartVerification(input.get("product2"));
		Assert.assertFalse(productFound); //Product will not be found so passing the case with false
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data=getJsonData(System.getProperty("user.dir")+"\\src\\main\\java\\arijit\\practice\\resources\\DataForTest2.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
