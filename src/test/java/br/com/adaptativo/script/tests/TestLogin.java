package br.com.adaptativo.script.tests;

import org.junit.Test;

import br.com.adaptativo.config.testbase.BaseTestAdaptativo;
import br.com.adaptativo.script.pages.PageLogin;

/**
 * 
 * Classe de testes com cenários relacionados a página de login
 * @author Jarbas
 * 
 * */
public class TestLogin extends BaseTestAdaptativo{

	PageLogin pageLogin = new PageLogin();
	
	@Test
	public void realizarLoginComSucesso () {
		pageLogin.realizarLogin();
	}
}
	
