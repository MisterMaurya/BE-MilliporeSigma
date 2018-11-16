package com.be.millipore.service;

import com.sendgrid.Response;

public interface EmailService {

	Response sendText(String from, String to, String subject, String body); // IF YOU WANT TO SEND TEXT ONLY

	Response sendHTML(String from, String to, String subject, String body); // IF YOU WANT TO SEND HTML CONTENT

	Response sendOtpTemplate(String from, String to, String subject, String templateId, String otp);

}