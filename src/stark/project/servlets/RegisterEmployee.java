package stark.project.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stark.project.dao.EmployeeDAO;
import stark.project.util.Users;

/**
 * Servlet implementation class RegisterEmployee
 */
@WebServlet("/RegisterEmployee")
public class RegisterEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Properties prop = getProperties();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users newUser = new Users();
		
		newUser.setFname(request.getParameter("fname"));
		newUser.setLname(request.getParameter("lname"));
		newUser.setUname(request.getParameter("uname"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setPass(generatePassword());
		
		EmployeeDAO.insertEmployee(newUser);
		
		Users user = EmployeeDAO.getInfo(request.getParameter("uname"));
		sendEmail(user.getEmail(),user.getId(),user.getUname(),user.getPass());
		
		response.sendRedirect("Managers/EmployeeList.html");
		
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
	
	private static void sendEmail(String email, String eid, String uName, String uPass) {    
		//System.out.println("Sending email to " + to);

	      Session session = createSession();

	      //create message using session
	      MimeMessage message = new MimeMessage(session);
	      String messageBody = "<h3>Congratulations on joining Stark, Stark, and Stark</h3>"
	      		+ "<p>Welcome to the Stark, Stark, and Stark family."
	      		+ "As a new employee you now have access to the employee"
	      		+ "reimbursement portal.Your information is listed below:</p>"
	      		+ "<p>Employee Id: " + eid + "<br>"
	      		+ "Username: " + uName + "<br> "
	      		+ "Password: " + uPass + "</p>";
	      try {
			message = prepareEmailMessage(message, email, "Welcome to Stark, Stark, and Stark", messageBody);
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
	    		  return new PasswordAuthentication(prop.getProperty("email.address"),prop.getProperty("email.password"));
	    	  }
	      });
	      return session;
	}
	
	private static MimeMessage prepareEmailMessage(MimeMessage message, String to, String title, String html)
	          throws MessagingException {
	      message.setContent(html, "text/html; charset=utf-8");
	      message.setFrom(new InternetAddress(prop.getProperty("email.address")));
	      message.setRecipients(Message.RecipientType.TO, to);
	      message.setSubject(title);
	      
	      return message;
	}
	
	private static Properties getProperties() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return props;
	}

}
