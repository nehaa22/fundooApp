package com.bridgeit.fundooapp.util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgeit.fundooapp.util.UserToken;
import com.bridgeit.fundooapp.user.model.Email;

public class MailService
{
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Autowired
	private UserToken userToken;
	
	/*-------------------------------------------------------
	 * Sending Mail
	 * -------------------------------------------------------*/
	
	public void send(Email email) 
	{
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(email.getTo()); 
	    message.setSubject(email.getSubject()); 
	    message.setText(email.getBody());
	    
	    System.out.println("Sending Email ");
	    
	    javaMailSender.send(message);

	    System.out.println("Email Sent Successfully!!");

	}
	
	/*-------------------------------------------------------
	 * Passing link to user
	 * -------------------------------------------------------*/
	
	public String getLink(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		return link+userToken.generateToken(id);
	}
	

}
