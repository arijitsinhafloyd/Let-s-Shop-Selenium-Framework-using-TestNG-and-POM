package arijit.practice.testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import arijit.practice.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent=ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> tl=new ThreadLocal<ExtentTest>();//to fix concurrency problem amd sync it up
	
	
	public void onTestStart(ITestResult result) {
		test=extent.createTest(result.getMethod().getMethodName());
		tl.set(test); //unique thread id
	}
	
	public void onTestSuccess(ITestResult result) {
		tl.get().log(Status.PASS, "Test Pass");
	}
	
	@SuppressWarnings("unchecked")
	public void onTestFailure(ITestResult result) {
		tl.get().fail(result.getThrowable());
		ThreadLocal<WebDriver> threadDriver = null;
		try {
			threadDriver=(ThreadLocal<WebDriver>) result.getTestClass().getRealClass()
					.getField("threadDriver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String filePath=null;
		try {
			filePath = screenshotImplementation(result.getMethod().getMethodName(),threadDriver.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tl.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
