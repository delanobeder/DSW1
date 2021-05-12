package br.ufscar.dc.dsw;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		EmailService service = new EmailService();
		
		InternetAddress from = new InternetAddress("<username>@gmail.com", "Fulano");
		InternetAddress to = new InternetAddress("<email>@<dominio>", "Beltrano");
				
		String subject1 = "Exemplo Subject (Gmail SMTP/Java)";
		String subject2 = "Exemplo Subject com Anexo (Gmail SMTP/Java)";

		String body1 = "Exemplo mensagem (Gmail SMTP/Java)";
		String body2 = "Exemplo mensagem com Anexo (Gmail SMTP/Java)";

		// Envio sem anexo
		service.send(from, to, subject1, body1);

		// Envio com anexo
		service.send(from, to, subject2, body2, new File("SIGA.pdf"));
	}
}