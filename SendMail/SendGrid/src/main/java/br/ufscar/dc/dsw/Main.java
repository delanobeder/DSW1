package br.ufscar.dc.dsw;

import java.io.File;
import java.io.IOException;

import com.sendgrid.helpers.mail.objects.Email;

public class Main {
	public static void main(String[] args) throws IOException {
		Email from = new Email("fulano@dsw.ufscar.br", "Fulano"); // Atualize
		Email to = new Email("beltrano@dsw.ufscar.br", "Beltrano"); // Atualize

		String subject1 = "Exemplo Subject (SendGrid/Java)";
		String subject2 = "Exemplo Subject com Anexo (SendGrid/Java)";

		String body1 = "Exemplo mensagem (SendGrid/Java)";
		String body2 = "Exemplo mensagem com Anexo (SendGrid/Java)";

		// Envio sem anexo
		EmailService.send(from, to, subject1, body1);

		// Envio com anexo
		EmailService.send(from, to, subject2, body2, new File("SIGA.pdf"));
	}
}