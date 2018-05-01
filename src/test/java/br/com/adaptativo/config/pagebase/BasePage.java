package br.com.adaptativo.config.pagebase;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import br.com.adaptativo.config.setup.MobileDriverFactory;
import br.com.adaptativo.config.setup.Property;
import br.com.adaptativo.config.setup.SO_Smartphone;
import br.com.adaptativo.config.util.Log;
import br.com.adaptativo.config.util.Utils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class BasePage {

	private static final int    LOAD_TIMEOUT          = 20;
	private static final int    INTERVALO_VERIFICACAO = 1;
	private String windowHandleJanelaInicial;
	
	public BasePage() {
			PageFactory.initElements(new AppiumFieldDecorator(MobileDriverFactory.getDriver(), 
			          LOAD_TIMEOUT, TimeUnit.SECONDS), this);
	}

	public void preencherCampo(WebElement element, String value) {
		try {
			isElementPresent(element);
			element.clear();
			element.sendKeys(value);
			if (Property.SO_SMARTPHONE.equals(SO_Smartphone.ANDROID)) {
				MobileDriverFactory.getAndroidDriver().pressKeyCode(AndroidKeyCode.ENTER);
			}else{
				//((AndroidDeviceActionShortcuts) MobileDriverFactory.getIosDriver()).pressKeyCode(IOSKeyCode.ENTER);
			}
		} catch (WebDriverException e) {
			erroPreenchimento(element, value);
		}
	}
	
	public void click(WebElement element) {
		try {
			isElementPresent(element);
			element.click();
		} catch (WebDriverException e) {
			erroClick(element);
		}
	}
	
	public String getTextAtributoElement(WebElement element) {
		try {
			isElementPresent(element);
			String s = element.getAttribute("value"); 
			return s;
		} catch (Exception e) {
			erroGetTextAtributo(element);
			return null;
		}
	}
	
	public String getTextElement(WebElement element) {
		try {
			isElementPresent(element);
			String s = element.getText(); 
			return s;
		} catch (Exception e) {
			erroGetText(element);
			return null;
		}
	}

//	public void confirmarAlerta() {
//		try {
//			Alert alert = DriverFactory.getDriver().switchTo().alert();
//			alert.accept();
//		} catch (Exception e) {
//			erroConfirmaAlerta();
//		}
//	}

//	public boolean isVisibility(By locator) {
//		try {
//			WebElement element = DriverFactory.getDriver().findElement(locator);
//			element.isDisplayed();
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
	
	public void selecionarValorComboTexto(WebElement element, String textVisible){
		try{
			isElementPresent(element);
			new Select(element).selectByVisibleText(textVisible);
		}catch(NoSuchElementException e){
			erroSelecaoCombo(element, textVisible);
		}
	}
	
	public void selecionarValorComboValue(WebElement element, String valueVisible){
		try{
			isElementPresent(element);
			new Select(element).selectByValue(valueVisible);
		}catch(NoSuchElementException e){
			erroSelecaoCombo(element, valueVisible);
		}
	}
//	public void clicarBotaoDireito(WebElement elemento) {
//		Actions action = new Actions(DriverFactory.getDriver());
//		action.contextClick(elemento).build().perform();
//	}
//	
//	public void doubleclick(WebElement elemento) {
//		Actions action = new Actions(DriverFactory.getDriver());
//		action.doubleClick().build().perform();
//	}
//
//	public void moverCursorPara(WebElement elemento) {
//		Actions action = new Actions(DriverFactory.getDriver());
//		aguardarElementoVisivel(elemento);
//		action.moveToElement(elemento).build().perform();
//	}
	
	public boolean existText(MobileElement elemento, String texto) {
		isElementPresent(elemento);
		return elemento.getText().contains(texto);
	}

//	public void voltarPagina() {
//		DriverFactory.getDriver().navigate().back();
//	}
//
//	public void alternarJanela() {
//		windowHandleJanelaInicial = DriverFactory.getDriver().getWindowHandle();
//		Set<String> windowHandles = DriverFactory.getDriver().getWindowHandles();
//		for (String windowHandle : windowHandles) {
//			if (!windowHandle.equals(windowHandleJanelaInicial)) {
//				DriverFactory.getDriver().switchTo().window(windowHandle);
//			}
//		}
//		setWindowHandleJanelaInicial(windowHandleJanelaInicial);
//	}
//	
//	public void retonarJanelaOriginal() {
//		DriverFactory.getDriver().switchTo().window(getWindowHandleJanelaInicial());
//	}

	public String getWindowHandleJanelaInicial() {
		return windowHandleJanelaInicial;
	}

	public void setWindowHandleJanelaInicial(String windowHandleJanelaInicial) {
		this.windowHandleJanelaInicial = windowHandleJanelaInicial;
	}

//	public WebElement getElement(By by) {
//		return DriverFactory.getDriver().findElement(by);
//	}
	
	public boolean isElementPresent(WebElement element){
        try{
            int counter = 0;
            while(counter < LOAD_TIMEOUT){
                Thread.sleep(INTERVALO_VERIFICACAO);
                counter++;
                try{
                    if(element.isDisplayed()){
                        return true;
                    }else{
                        continue;
                    }
                }catch(Exception e){
                    continue;
                }
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }
	
	public void erroPreenchimento(WebElement element, String value) {
		erro();
		Log.erro(element.toString().substring(45, element.toString().length()-2)+"]. não encontrado, valor ["+value+"] não pôde ser preenchido.");
		Assert.fail(element.toString().substring(45, element.toString().length()-2)+"]. não encontrado, valor ["+value+"] não pôde ser preenchido.");
		
	}
	
	public void erroEspera(WebElement element) {
		erro();
		Log.erro("Tempo excedido ("+LOAD_TIMEOUT+"s) para aguardar elemento: "+element.toString().substring(45, element.toString().length()-1)+"");
		Assert.fail("Tempo excedido ("+LOAD_TIMEOUT+"s) para aguardar elemento: "+element.toString().substring(45, element.toString().length()-1)+"");
	}
	
	public void erroClick(WebElement element) {
		erro();
		Log.erro("Erro ao clicar no elemento: "+element.toString().substring(45, element.toString().length()-2)+"].");
		Assert.fail("Erro ao clicar no elemento "+element.toString().substring(45, element.toString().length()-2)+"].");
	}
	
	public void erroGetTextAtributo(WebElement element) {
		erro();
		Log.erro("Erro ao buscar texto de atributo do elemento: "+element.toString().substring(45, element.toString().length()-2)+"].");
		Assert.fail("Erro ao buscar texto de atributo do elemento: "+element.toString().substring(45, element.toString().length()-2)+"].");
	}
	
	public void erroGetText(WebElement element) {
		erro();
		Log.erro("Erro ao buscar texto do elemento: "+element.toString().substring(45, element.toString().length()-2)+"].");
		Assert.fail("Erro ao buscar texto do elemento: "+element.toString().substring(45, element.toString().length()-2)+"].");
	}
	
	public void erroConfirmaAlerta() {
		erro();
		Log.erro("Erro ao realizar a confirmacao do Alerta");
		Assert.fail("Erro ao realizar a confirmacao do Alerta");
	}
	
	public void errogetText() {
		erro();
		Log.erro("Erro ao realizar a confirmacao do Alerta");
		Assert.fail("Erro ao realizar a confirmacao do Alerta");
	}
	
	public void erroSelecaoCombo(WebElement element, String valor) {
		erro();
		Log.erro("Erro ao selecionar elemento do combo: "+element.toString().substring(45, element.toString().length()-2)+"], com o valor: "+valor);
		Utils.takeScreenshot("Combobox-"+valor);
		Assert.fail("Erro ao selecionar elemento do combo: "+element.toString().substring(45, element.toString().length()-2)+"], com o valor: "+valor);
	}
	
	public void erro() {
		Log.erro("E R R O ...");
	}
}