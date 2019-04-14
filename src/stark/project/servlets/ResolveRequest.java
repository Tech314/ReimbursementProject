package stark.project.servlets;

import java.io.IOException;
import java.sql.Date;
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
import javax.servlet.http.HttpSession;

import stark.project.dao.EmployeeDAO;
import stark.project.dao.ManagerDAO;
import stark.project.dao.RequestDAO;
import stark.project.util.Requests;
import stark.project.util.Users;

/**
 * Servlet implementation class ResolveRequest
 */
@WebServlet("/ResolveRequest")
public class ResolveRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResolveRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int reqId = Integer.parseInt(request.getParameter("reqId"));
		String decision = request.getParameter("decision");
		Users manager = ManagerDAO.getInfo((String)session.getAttribute("user"));
		
		Requests req = RequestDAO.getRequest(reqId);
		
		req.setManId(Integer.parseInt(manager.getId()));
		req.setReqDecision(decision);
		
		RequestDAO.resolveRequest(req);
		
		Users employee = EmployeeDAO.getInfo(req.getEmpId());
		
		sendEmail(employee.getEmail(),manager.getFname(),manager.getLname(),decision,req.getReqDate(),req.getReqAmt());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static void sendEmail(String email, String mFName, String mLName, String decision,Date reqDate,double reqAmt) {    
		//System.out.println("Sending email to " + to);

	      Session session = createSession();

	      //create message using session
	      MimeMessage message = new MimeMessage(session);
	      String messageBody = "<h3>Resolved Request</h3>"
	      		+ "<p>You're request made on " + reqDate + "for $" + reqAmt
	      		+ " has been " + decision + "ed by the following manager: "
	      		+ mFName + " " + mLName + "</p>";
	      try {
			message = prepareEmailMessage(message, email, "Resolved Request", messageBody);
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
