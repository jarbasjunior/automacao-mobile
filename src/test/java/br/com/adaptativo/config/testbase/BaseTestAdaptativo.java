package br.com.adaptativo.config.testbase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import br.com.adaptativo.config.setup.MobileDriverFactory;
import br.com.adaptativo.config.suite.AllTests;
import br.com.adaptativo.config.util.Log;
public class BaseTestAdaptativo {
	
	
	//protected static MobileDriver driver;
	
	@Rule
	public TestName nameTest = new TestName();
	
	@Before
	public void before(){
		Log.msgInicioTeste(nameTest.getMethodName());
	}

	@After
	public void after(){
		Log.msgFimTeste(nameTest.getMethodName());
	}
	
	@BeforeClass
	public static void beforeClass(){
		if(!AllTests.isAllTestsExecution){
			MobileDriverFactory.startServer();
			MobileDriverFactory.getDriver();
		}
	}
	
	@AfterClass
	public static void afterClass(){
		if(!AllTests.isAllTestsExecution){
			MobileDriverFactory.resetDriver();
		}
	}
	
}
