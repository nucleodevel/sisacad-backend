package org.nucleodevel.sisacad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private static final String DEFAULT_MAIL_FROM = "noreply@sisacad.hopto.org";

	@Autowired
	private JavaMailSender mailSender;

	public void send(String from, String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		try {
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String to, String subject, String text) {
		send(DEFAULT_MAIL_FROM, to, subject, text);
	}

}
