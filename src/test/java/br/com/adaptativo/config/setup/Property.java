package br.com.adaptativo.config.setup;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
/**
* @author jarbas.junior
* Define o caminho do driver dos diferentes browsers
* Acessa as IDENTIFICAÇÕES definidas no config.properties e retorna a informação 
*/
public abstract class Property {

	/*
	 * IDENTIFICAÇÕES DE ACESSO AO SISTEMA
	 */
	public static final String PASSWORD;
	public static final String USR_PAI;
	public static final String USR_ALUNO;
	public static final String USR_PROFESSOR;
	public static final String STUDENT_NAME;
		
	/*
	 * DADOS DO APP
	 */
	
	public static final String APPIUM_SERVICE;
	public static final String SO_SMARTPHONE;
	public static final String MOBILE_APPLICATION;
	public static final String MOBILE_APPLICATION_PATH;
	public static final String EVIDENCIAS_TESTE_PATH;

	/*
	 * IDENTIFICAÇÕES DA PLANILHA DE DADOS
	 */
	public static final String ARQUIVO_TESTE_XLS;
	public static final String PATH_ARQUIVO_TESTE;

	private static final String PROPERTIES_FILE = "br/com/adaptativo/config.properties";
	
	//\\src\\test\\resources\\br\\com\\adaptativo
	
	static{
		STUDENT_NAME            = get("student.name");
		PASSWORD				= get("password");
		USR_PAI          		= get("usr.pai");
		USR_ALUNO    	        = get("usr.aluno");
		USR_PROFESSOR           = get("usr.professor");
		SO_SMARTPHONE           = get("so.smartphone");
		MOBILE_APPLICATION      = get("application");
		APPIUM_SERVICE          = get("appium.service");
		MOBILE_APPLICATION_PATH = new File("").getAbsolutePath() + "\\src\\test\\resources\\apps\\";  
		EVIDENCIAS_TESTE_PATH   = new File("").getAbsolutePath() + "\\src\\test\\resources\\evidencias\\";
		ARQUIVO_TESTE_XLS       = get("arquivo.teste.xls");
		PATH_ARQUIVO_TESTE      = get("path.arquivo.xls");
	}
	
	/**
	 * Metodo para pegar o valor de alguma propriedade no arquivo de configuracao do Selenium
	 * O caminho e o nome do arquivo pode ser trocados
	 */
	private static String get(String name) {
		Properties properties = new Properties();
		String     value      = null;
		try {
			properties.load(Property.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		    value = properties.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
