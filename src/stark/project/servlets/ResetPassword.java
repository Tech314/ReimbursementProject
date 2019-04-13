package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import stark.project.dao.EmployeeDAO;
import stark.project.util.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Servlet implementation class ResetPassword
 */
@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession(false);
		Users employee = EmployeeDAO.getInfo(Integer.parseInt(request.getParameter("eid")));
		int eid = Integer.parseInt(request.getParameter("eid"));
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String newPass = generatePassword();
		
		if(EmployeeDAO.resetPass(eid,newPass)) {
			out.print("Successfully reset password");
			sendEmail(employee.getEmail(),newPass);
		}
		else {
			out.print("Error resetting password");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String generatePassword() {
		String passwordset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
		char[] password = new char[13];
		for(int i = 0; i < 13; i++) {
			password[i] = passwordset.charAt((int)(Math.random()*67));
		}
		return new String(password);
	}
	
	private static void sendEmail(String email, String newPass) {    
		//System.out.println("Sending email to " + to);

	      Session session = createSession();

	      //create message using session
	      MimeMessage message = new MimeMessage(session);
	      String messageBody = "<h3>Your Password has been reset</h3>"
	      		+ "<p>Your new password is: " + newPass + "</p>";
	      try {
			message = prepareEmailMessage(message, email, "Password Reset", messageBody);
			//sending message
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      
	      //System.out.println("Done");
	}
	
	private static Session createSession() {
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
	      //props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
	      props.put("mail.smtp.ssl.enable", "true");
	      props.put("mail.smtp.host", "smtp.mail.yahoo.com"); //Outgoing server (SMTP) - change it to your SMTP server
	      props.put("mail.smtp.port", "465");//Outgoing port
	      props.put("mail.smtp.socketFactory.class", 
	                "javax.net.ssl.SSLSocketFactory");
	      
	    //  Session session = Session.getInstance(props);
	      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	    	  protected PasswordAuthentication getPasswordAuthentication() {
	    		  return new PasswordAuthentication("noreplystarkstarkstark@yahoo.com","oracle123");
	    	  }
	      });
	      return session;
	}
	
	private static MimeMessage prepareEmailMessage(MimeMessage message, String to, String title, String html)
	          throws MessagingException {
	      message.setContent(html, "text/html; charset=utf-8");
	      message.setFrom(new InternetAddress("noreplystarkstarkstark@yahoo.com"));
	      message.setRecipients(Message.RecipientType.TO, to);
	      message.setSubject(title);
	      
	      return message;
	}

}
