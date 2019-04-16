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

import stark.project.dao.ManagerDAO;
import stark.project.util.Users;

/**
 * Servlet implementation class EditManagerInfo
 */
@WebServlet("/EditManagerInfo")
public class EditManagerInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditManagerInfo() {
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
		
		Users user = ManagerDAO.getInfo(uname);
		
		if(request.getParameter("newPass").equals(request.getParameter("newPass2")) 
				&& !request.getParameter("newPass").equals("")) {
			if(request.getParameter("oldPass").equals(user.getPass())) {
				user.setPass(request.getParameter("newPass"));
				ManagerDAO.editManInf(user);
			//	out.print("Info updated successfully");
				request.setAttribute("success", true);
				RequestDispatcher rd = request.getRequestDispatcher("EditInfoLanding");
				rd.include(request, response);
			}
			else {
				request.setAttribute("success", false);
				RequestDispatcher rd = request.getRequestDispatcher("EditInfoLanding");
				rd.include(request, response);
			}
		}
		if(!request.getParameter("newPass").equals(request.getParameter("newPass2")) 
				&& !request.getParameter("newPass").equals("")
				&& !request.getParameter("newPass2").equals("")){
		//	out.print("New passwords don't match");
			request.setAttribute("success", false);
			RequestDispatcher rd = request.getRequestDispatcher("EditInfoLanding");
			rd.include(request, response); 
		}
		
		/*if(!request.getParameter("newEmail").equals("")) {
			user.setEmail(request.getParameter("newEmail"));
		}
		
		if(request.getParameter("oldPass").equals(user.getPass())) {
			ManagerDAO.editManInf(user);
		//	out.print("Info updated successfully");
			request.setAttribute("success", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditInfoLanding");
			rd.include(request, response);
		}
		if(!request.getParameter("oldPass").equals(user.getPass())) {
		//	out.print("Old password does not match existing password");
			request.setAttribute("success", false);
			RequestDispatcher rd = request.getRequestDispatcher("EditInfoLanding");
			rd.include(request, response);
		} */
		
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
