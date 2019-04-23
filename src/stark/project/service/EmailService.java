package stark.project.service;

import java.sql.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public interface EmailService {
	
	Session createSession();
	MimeMessage prepareEmailMessage(MimeMessage message, String to, String title, String html);
	Properties getProperties();
	void sendEmail(String email, String mFName, String mLName, String decision,Date reqDate,double reqAmt);
	void sendEmail(String email, String newPass);
	void sendEmail(String email, String eid, String uName, String uPass);
	
}
