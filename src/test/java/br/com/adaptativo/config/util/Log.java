package br.com.adaptativo.config.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class Log {

	private static final Logger aLogger = LogUtils.getLogger();
	private static final SimpleDateFormat aFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public static void info(String pMensagem) {
		try {
			mensagem(getCabecalho("INFO") + pMensagem);
		} catch (Exception e) {
			mensagemErro(pMensagem, e);
		}
	}
	
	public static void erro(String pMensagem) {
		try {
			String vMensagemFinal = getCabecalho("ERRO") + pMensagem;
			mensagemErro(vMensagemFinal);
		} catch (Exception e) {
			mensagemErro(pMensagem, e);
		}
	}

	public static void erro(String string, Exception e) {
		mensagemErro(getCabecalho("ERRO") + string,e);
	}
	
	private static String getCabecalho(String pTipo) {
		return String.format("[%s] [%s] ", pTipo, aFormat.format(Calendar.getInstance().getTime()));
	}
	private static void mensagem(String pMensagemFinal) {
		aLogger.info(pMensagemFinal);
	}
	public static void mensagemErro(String pMensagemFinal) {
		aLogger.error(pMensagemFinal);
	}
	public static void fail(String pMensagemFinal) {
		aLogger.fatal(pMensagemFinal);
	}
	public static void mensagemErro(String pMensagemFinal,Exception e) {
		aLogger.error(pMensagemFinal,e);
	}
	public static void msgInicioTeste(String nomeTeste){
		info("=======================================================================================");
		info("Iniciando teste: ["+nomeTeste+"]");
	}
	public static void msgFimTeste(String nomeTeste){
		info("Fim do teste: ["+nomeTeste+"]");
		info("=======================================================================================");
	}
}
