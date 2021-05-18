package com.codename1.sendgrid;


/**
 *  Simple API for sending an email via sendgrid
 * 
 *  @author Shai Almog
 */
public class SendGrid {

	/**
	 *  You need the API token from send grid to use this class, it's available 
	 *  by signing up to their service
	 *  @param token the API token
	 *  @return the class instance
	 */
	public static SendGrid create(String token) {
	}

	/**
	 *  Sends an email synchronously 
	 *  @param to to email
	 *  @param from from email
	 *  @param subject email subject
	 *  @param body the body of the email
	 */
	public void sendSync(String to, String from, String subject, String body) {
	}

	/**
	 *  Sends an email synchronously 
	 *  @param to to email
	 *  @param from from email
	 *  @param subject email subject
	 *  @param body the body of the email in html format
	 */
	public void sendHtmlSync(String to, String from, String subject, String body) {
	}

	/**
	 *  Sends an email asynchronously 
	 *  @param to to email
	 *  @param from from email
	 *  @param subject email subject
	 *  @param body the body of the email
	 */
	public void sendASync(String to, String from, String subject, String body) {
	}

	/**
	 *  Sends an email asynchronously 
	 *  @param to to email
	 *  @param from from email
	 *  @param subject email subject
	 *  @param body the body of the email in html format
	 */
	public void sendHtmlASync(String to, String from, String subject, String body) {
	}
}
