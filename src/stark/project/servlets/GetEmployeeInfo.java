package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.*;
import stark.project.util.Users;

import stark.project.dao.EmployeeDAO;

/**
 * Servlet implementation class GetEmployeeInfo
 */
@WebServlet("/GetEmployeeInfo")
public class GetEmployeeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmployeeInfo() {
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
		
		if(session != null) {
			uname = (String) session.getAttribute("user");
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper map = new ObjectMapper();
		Users user = EmployeeDAO.getInfo(uname);
		//System.out.println(map.writeValueAsString(user));
		out.print(map.writeValueAsString(user));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
