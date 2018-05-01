package br.com.adaptativo.config.setup;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.adaptativo.config.util.Log;
import br.com.adaptativo.config.util.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;


/**
 * Classe utilizada como fpabrica de drivers para os testes
 * Identifica qual o SO do smartphone será escolhido no 
 * config.properties e inicializa o webdriver correspondente
 */
public class MobileDriverFactory {
	
	/**
	 * Verifica qual o S.0 escolhido no arquivo de propriedades;
	 * inicializa o driver apropriado;
	 * @return E retorna instância do Driver de acordo com o S.O. escolhido
	 */
	
	private static IOSDriver<WebElement>     iosDriver       = null;
	private static AndroidDriver<WebElement> androidDriver   = null;
	private static String                    so              = Property.SO_SMARTPHONE;
	private static DesiredCapabilities 		 capacidade      = new DesiredCapabilities();
	private static File                		 pathApplication = new File(Property.MOBILE_APPLICATION_PATH);
	private static File                		 applicationFile = new File(pathApplication, Property.MOBILE_APPLICATION);
	
	public static SearchContext getDriver() {
		if (so.equals(SO_Smartphone.ANDROID)) {
			return getAndroidDriver();
		}else 
			return getIosDriver();
	}	
		
	public static void resetDriver(){
		if (so.equals(SO_Smartphone.ANDROID)) {
			resetAndroidDriver();
		}else if (so.equals(SO_Smartphone.IOS))
			resetIosDriver();
	}
	
	public static AndroidDriver<WebElement> getAndroidDriver() {
		if (androidDriver == null) {
			capacidade.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
			capacidade.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
			capacidade.setCapability(MobileCapabilityType.APP, applicationFile.getAbsolutePath());
			try {
				androidDriver = new AndroidDriver<WebElement>(new URL(Property.APPIUM_SERVICE), capacidade);
			} catch (Exception e) {
				msgErro();
			}
		}
		return androidDriver;
	}
	
	public static IOSDriver<WebElement> getIosDriver() {
		if (iosDriver == null) {
			capacidade.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			capacidade.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
			capacidade.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
			try {
				iosDriver = new IOSDriver<WebElement>(new URL(Property.APPIUM_SERVICE), capacidade);
			} catch (Exception e) {
				msgErro();
			}
		}
		return iosDriver;
	}

	public static void resetAndroidDriver() {
		if (androidDriver != null) {
			androidDriver.close();
		}
		androidDriver = null;
		stopServer();
	}
	
	public static void resetIosDriver() {
		if (iosDriver != null) {
			iosDriver.close();
		}
		iosDriver = null;
		stopServer();
	}
	
	public static void startServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			//também maximiza o prompt
			//runtime.exec("cmd.exe /c start /max cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stopServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Log.info("Finalizando serviço do Appium...");
			runtime.exec("taskkill /F /IM node.exe");
			runtime.exec("taskkill /F /IM cmd.exe");
			Log.info("Serviço finalizado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void msgErro(){
		Log.erro("E R R O ao iniciar MobileDriver ! ! !, veriicar depuração USB, conexão ou caminho do app.");
		Utils.assertFail("E R R O ao iniciar MobileDriver ! ! !, veriicar depuração USB, conexão ou caminho do app.");
	}
	
}
