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

import stark.project.dao.EmployeeDAO;
import stark.project.util.Users;

/**
 * Servlet implementation class EditEmployeeInfo
 */
@WebServlet("/EditEmployeeInfo")
public class EditEmployeeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEmployeeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String uname = null;
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
		
		if(session != null) {
			uname = (String) session.getAttribute("user");
		}
		
		Users user = EmployeeDAO.getInfo(uname);
		
		if(request.getParameter("newPass").equals(request.getParameter("newPass2")) 
				&& !request.getParameter("newPass").equals("")) {
			user.setPass(request.getParameter("newPass"));
		}
		if(!request.getParameter("newPass").equals(request.getParameter("newPass2")) 
				&& !request.getParameter("newPass").equals("")
				&& !request.getParameter("newPass2").equals("")){
			out.print("New passwords don't match");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("./Employees/EmployeeInfoEdit.html");
			rd.include(request, response);
		}
		
		if(!request.getParameter("newEmail").equals("")) {
			user.setEmail(request.getParameter("newEmail"));
		}
		
		if(request.getParameter("oldPass").equals(user.getPass())) {
			EmployeeDAO.editEmpInf(user);
			out.print("Info updated successfully");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("./Employees/EmployeeInfoEdit.html");
			rd.include(request, response);
		}
		if(!request.getParameter("oldPass").equals(user.getPass())) {
			out.print("Old password does not match existing password");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("./Employees/EmployeeInfoEdit.html");
			rd.include(request, response);
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
