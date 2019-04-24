package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import stark.project.dao.*;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="Login", urlPatterns="/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    String t = request.getParameter("usertype");
	    String n = request.getParameter("username");  
	    String p = request.getParameter("userpass");
	    
	    if(t.equals("Employee") && EmployeeDAO.validate(n, p)) {
	    	HttpSession session = request.getSession();
			session.setAttribute("user", n);
			session.setAttribute("userType", t);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			response.sendRedirect("Employees/EmployeeHome.html");
	    }
	    else if(t.equals("Manager") && ManagerDAO.validate(n, p)) {
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
	        rd.include(request,response);  
	    }  
	          
	    out.close();  
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
