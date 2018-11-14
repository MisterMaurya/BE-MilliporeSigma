package com.be.millipore.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.service.EmailService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailServiceImpl implements EmailService {

	private SendGrid sendGridClient;

	@Autowired
	public EmailServiceImpl(SendGrid sendGridClient) {
		this.sendGridClient = sendGridClient;
	}

	@Override
	public Response sendText(String from, String to, String subject, String body) {
		Response response = sendEmail(from, to, subject, new Content("text/plain", body));
		System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
				+ response.getHeaders());
		return response;
	}

	@Override
	public Response sendHTML(String from, String to, String subject, String body) {
		Response response = sendEmail(from, to, subject, new Content("text/html", body));
		System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
				+ response.getHeaders());
		return response;
	}

	private Response sendEmail(String from, String to, String subject, Content content) {
		Mail mail = new Mail(new Email(from), subject, new Email(to), content);
		mail.setReplyTo(new Email("no-reply@gmail.com"));
		Request request = new Request();
		Response response = null;
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			response = this.sendGridClient.api(request);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return response;
	}
}