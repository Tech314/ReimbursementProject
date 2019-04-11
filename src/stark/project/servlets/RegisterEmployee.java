package stark.project.servlets;

import java.io.IOException;
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

}
