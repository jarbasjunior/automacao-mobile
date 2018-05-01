package br.com.adaptativo.script.pages;

import org.openqa.selenium.WebElement;

import br.com.adaptativo.config.pagebase.BasePage;
import br.com.adaptativo.config.setup.Property;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PageLogin extends BasePage {

	public PageLogin() {}

	@AndroidFindBy(id = "br.com.fractaltecnologia.adaptativo:id/imgLoginLogo")
	private WebElement logoAdaptativo;
	
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.TextView")
	private WebElement btEntrar;
	
	@AndroidFindBy(id = "br.com.fractaltecnologia.adaptativo:id/edtLoginEmail")
	private WebElement inputIdOrEmail;

	@AndroidFindBy(id = "br.com.fractaltecnologia.adaptativo:id/edtLoginPassword")
	private WebElement inputSenha;
	
	public void realizarLogin() {
		enterAsStaging();
		preencherCampo(inputIdOrEmail, Property.USR_ALUNO);
		preencherCampo(inputSenha    , Property.PASSWORD);
		click(btEntrar);
	}
	
	public void enterAsStaging(){
		for (int i = 0; i < 5; i++) {
			//aguardarElementoVisivel(logoAdaptativo);
			click(logoAdaptativo);
		}
		click(btEntrar);
	}
}	
