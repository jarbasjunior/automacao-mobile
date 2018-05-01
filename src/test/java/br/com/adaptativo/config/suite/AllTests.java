package br.com.adaptativo.config.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;

import br.com.adaptativo.config.setup.MobileDriverFactory;
import br.com.adaptativo.script.tests.TestLogin;

/**
 * Classe que agrupa todas as classes de teste, funcionando com uma suíte de regressão.
 * @author Jarbas
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestLogin.class
})

public class AllTests {
	
protected static WebDriver driver;
	
	/*public static Date    fim    			  = null;
	public static Date    inicio 			  = null;*/
	public static Boolean isAllTestsExecution = false;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		//inicio = Calendar.getInstance().getTime();
		isAllTestsExecution = true;
		MobileDriverFactory.getDriver();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		MobileDriverFactory.resetDriver();
		/*fim = Calendar.getInstance().getTime();
		Utils.calculaTempoDoTest(inicio, fim);*/
	}

}
