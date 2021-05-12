package br.ufscar.dc.dsw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailService {
	
	public void send(Email from, Email to, String subject, String body, File file) throws IOException {

		try {
			Properties prop = new Properties();
			InputStream is = EmailService.class.getClassLoader().getResourceAsStream("config.properties");

			if (is != null) {
				prop.load(is);
			} else {
				throw new FileNotFoundException("config.properties not found in the classpath");
			}

			SendGrid sg = new SendGrid(prop.getProperty("API_KEY"));
			
			Content content = new Content("text/plain", body);

			Mail mail = new Mail(from, subject, to, content);
			
			if (file != null) {
				String fileName = file.getName();
				Attachments attachments = new Attachments();
			    attachments.setFilename(fileName);
			    String type = "";
			    
			    int i = fileName.lastIndexOf('.');
			    if (i > 0) {
			        type = fileName.substring(i+1);
			    }
			    					    		
			    attachments.setType("application/" + type);
			    attachments.setDisposition("attachment");

			    byte[] attachmentContentBytes = Files.readAllBytes(file.toPath());
			    String s = Base64.getEncoder().encodeToString(attachmentContentBytes);
			    attachments.setContent(s);
			    mail.addAttachments(attachments);
			}

			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			
			Response response = sg.api(request);

			if (response.getStatusCode() == 202) {
				System.out.println("Mensagem enviada com sucesso!");
			} else {
				System.out.println("Mensagem não enviada!");
				System.out.println(response.getStatusCode());
				System.out.println(response.getBody());
				System.out.println(response.getHeaders());
			}
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	public void send(Email from, Email to, String subject, String body) throws IOException {
		send(from, to, subject, body, null);
	}
}
