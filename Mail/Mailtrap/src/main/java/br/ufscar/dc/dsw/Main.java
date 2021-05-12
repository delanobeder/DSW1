package br.ufscar.dc.dsw;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {

		EmailService service = new EmailService();
		
		InternetAddress from = new InternetAddress("admin@dsw.com", "Administrator");
		InternetAddress to = new InternetAddress("bob@dsw.com", "Bob");
		
		String subject1 = "Exemplo Subject (Mailtrap/Java)";
		String subject2 = "Exemplo Subject com Anexo (Mailtrap/Java)";

		String body1 = "Exemplo mensagem (Mailtrap/Java)";
		String body2 = "Exemplo mensagem com Anexo (Mailtrap/Java)";
		
		// Envio sem anexo
		service.send(from, to, subject1, body1);

		// Envio com anexo
		service.send(from, to, subject2, body2, new File("SIGA.pdf"));
	}
}