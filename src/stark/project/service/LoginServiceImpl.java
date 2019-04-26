package stark.project.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import stark.project.dao.EmployeeDAO;
import stark.project.dao.Login;
import stark.project.dao.ManagerDAO;

public class LoginServiceImpl implements LoginService {
	
	private final Login edao = new EmployeeDAO();
	private final Login mdao = new ManagerDAO();

	@Override
	public void attemptValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    String t = request.getParameter("usertype");
	    String n = request.getParameter("username");  
	    String p = request.getParameter("userpass");
	    
	    if(t.equals("Employee") && edao.validate(n, p)) {
	    	HttpSession session = request.getSession();
			session.setAttribute("user", n);
			session.setAttribute("userType", t);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			response.sendRedirect("Employees/EmployeeHome.html");
	    }
	    else if(t.equals("Manager") && mdao.validate(n, p)) {
	    	HttpSession session = request.getSession();
			session.setAttribute("user", n);
			session.setAttribute("userType", t);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			response.sendRedirect("Managers/ManagerHome.html");
	    }
	    else{  
	        out.print("Username/Password combination does not exist");  
	        RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");  
	        try {
				rd.include(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }  
	          
	    out.close(); 
		
	}

}
