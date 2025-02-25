package br.ufscar.dc.dsw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

	public void send(InternetAddress from, InternetAddress to, String subject, String body, File file) {

		try {

			Properties prop = new Properties();
			InputStream is = Main.class.getClassLoader().getResourceAsStream("config.properties");

			if (is != null) {
				prop.load(is);
			} else {
				throw new FileNotFoundException("config.properties not found in the classpath");
			}

			String username = prop.getProperty("username");
			String password = prop.getProperty("password");

			Session session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(from);
			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(subject);

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(body, "text/plain");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			if (file != null) {
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				attachmentBodyPart.attachFile(file);
				multipart.addBodyPart(attachmentBodyPart);	
			}
			
			message.setContent(multipart);
			Transport.send(message);
			
			System.out.println("Mensagem enviada com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Mensagem não enviada!");
			e.printStackTrace();
		}
	}
	
	public void send(InternetAddress from, InternetAddress to, String subject, String body) {
		send(from, to, subject, body, null);
	}
}
