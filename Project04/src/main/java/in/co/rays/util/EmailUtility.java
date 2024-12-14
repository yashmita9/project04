package in.co.rays.util;

import java.util.Properties;
import java.util.ResourceBundle;

public class EmailUtility {

	static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.bundle.system");
	
	private static final String SMTP_HOST_NAME = rb.getString("smtp.server") ;
	private static final String SMTP_PORT = rb.getString("smtp.port");
	private static final String emailFromAddress = rb.getString("email.login");
	private static final String emailPassword = rb.getString("email.pwd");

	private static Properties props = new Properties();

	static {
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
	
	}
}
