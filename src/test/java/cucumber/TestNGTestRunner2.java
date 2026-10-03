package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/cucumber/ErrorValidations.feature",glue="cucumber", monochrome=true, plugin= {"html:target/cucumber.html"})
public class TestNGTestRunner2 extends AbstractTestNGCucumberTests {

}
