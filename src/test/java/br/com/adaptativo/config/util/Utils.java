package br.com.adaptativo.config.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import javax.imageio.ImageIO;

import org.junit.Assert;

import com.github.javafaker.Faker;

import br.com.adaptativo.config.setup.Property;

/**
 * Classe com m�todos de apoio, que otimizam a codifica��o das classes de
 * p�gina.
 * 
 * @author jarbas.junior
 *
 */
public abstract class Utils {

	private static boolean isError = false;

	public static void wait(final int iTimeInMillis) {
		try {
			Thread.sleep(iTimeInMillis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void assertEquals(Object atual, Object esperado) {
		try {
			isError = !esperado.toString().equals(atual.toString());
			Assert.assertEquals(esperado, atual);
		} catch (Exception e) {
			Log.info("    ||");
			Log.info("   \\  /");
			Log.info("    **");
			Log.erro("E R R O . . .");
			Log.erro("Esperava-se: ["+esperado+"]");
			Log.erro("E retornou.: ["+atual+"]");
			takeScreenshot(removeCaracterEspecial(esperado.toString())+"#"+removeCaracterEspecial(atual.toString()));
			takeScreenshot(esperado.toString()+"#"+atual.toString());
			assertFail("Erro encontrado: Esperado ["+esperado+"], mas retornou ["+atual+"]");
		}
	}
	
	public static void assertTrue(String message, boolean bol){
		try {
			isError = !bol;
			Assert.assertTrue(message, bol);
		} catch (Exception e) {
			takeScreenshot(removeCaracterEspecial(message));
			assertFail(message);
		}finally{
			if (isError) {
				Log.erro("E R R O . . .");
				Log.erro(message);
				takeScreenshot(removeCaracterEspecial(message));
			}
		}
	}
	
	public static void assertFail(String message) {
		Assert.fail(message);
	}
	
	public static String conversorDoubleString(double valor){
		DecimalFormat df = new DecimalFormat("#,###.00");  
		String novoValor = df.format(valor);
		return novoValor;
	}
	
	public static Double conversorStringDouble(String valorString){
		String valorDouble = valorString.replace(",", "");
		return Double.valueOf(valorDouble);
	} 
	
	public static String converterValoresFloatToString(Float value) {
		NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf.format(value).replace(",","");
	}
	
	public static String geraSigla(int tamanhoSigla){
		Random random = new Random();
		char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tamanhoSigla; i++) {
			int ch = random.nextInt (letras.length);
		    sb.append (letras [ch]);
		}    
		return sb.toString();    
	}
	
	public static String converteInteiroParaString(int numero){
		return Integer.toString(numero);
	}
	
	public static int converteStringParaInt(String str){
		return Integer.parseInt(str);
	}
	
	public static String removeCaracterEspecial(String s) {
		s = s.replace(":", "");
		s = s.replace(";", "");
		s = s.replace("/", "");
		s = s.replace("\\", "");
		s = s.replace("ç", "c");
		s = s.replace("{", "c");
		s = s.replace("}", "c");
		s = s.replace("[", "c");
		s = s.replace("]", "c");
		return s;
	}
	
	public static void takeScreenshot(String fileName){
		Date data = new Date();
		
	    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy hh mm ss a");
        Robot robot;
		try {
			robot = new Robot();
         	BufferedImage screenShot = robot.createScreenCapture(new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
         	try {
				ImageIO.write(screenShot, "png", new File(Property.EVIDENCIAS_TESTE_PATH+fileName+ "_"+formatter.format(data)+".png"));
			} catch (IOException e) {
				Log.erro("Erro ao fotografar bug em tela");
			}
		} catch (AWTException e) {
			Log.erro("Erro ao fotografar bug em tela");
		}
	}
	
	public static String getAnoAnterior() {
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
			Calendar calendar = new GregorianCalendar();
			Date d1 = new Date();
			calendar.setTime(d1);
			calendar.add(Calendar.DATE, -600);
			Date d2 = calendar.getTime();
			return formatDate.format(d2);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getDataAtual() {
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = new GregorianCalendar();
			Date data = new Date();
			calendar.setTime(data);
			return formatDate.format(data);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getAno(int qtdAnosAtras) {
		int dias = qtdAnosAtras * 365;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
			Calendar calendar = new GregorianCalendar();
			Date d1 = new Date();
			calendar.setTime(d1);
			calendar.add(Calendar.DATE, -dias);
			Date d2 = calendar.getTime();
			return formatDate.format(d2);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String geraNomeEmpresa(){
		Faker fake = new Faker();
		return fake.company().name();
	}
	
	public static String geraNomePortador() {
		Faker fake = new Faker();
		return fake.name().firstName()+" "+fake.name().lastName();
	}
	
	public static String geraNomeBanco(){
		Faker fake = new Faker();
		return fake.business().creditCardType().toUpperCase();
	}
	
	public static String geraNumeroConta(){
		Faker fake = new Faker();
		return fake.number().digits(5);
	}
	
	public static String geraNumeroAgencia(){
		Faker fake = new Faker();
		return fake.number().digits(4);
	}
	
	public static String geraSaldoAtual(){
		Faker fake = new Faker();
		StringBuilder sb = new StringBuilder(fake.number().digits(2));
		sb.insert(0 , fake.number().randomDigitNotZero());
		sb.insert(3 , ",");
		return sb.toString() + "00";
	}
	
	public static void calculaTempoDoTest(Date tempoInicio, Date tempoFinal) {
		long diferencaHoras    = (tempoFinal.getTime() - tempoInicio.getTime()) / (1000 * 60 * 60);
		long diferencaMinutos  = (tempoFinal.getTime() - tempoInicio.getTime()) / (1000 * 60);
		long diferencaSegundos = (tempoFinal.getTime() - tempoInicio.getTime()) / (1000);
		Log.info("Tempo de execucao:"+ String.format("%02d:%02d:%02d ", diferencaHoras, diferencaMinutos, diferencaSegundos));
	}
}