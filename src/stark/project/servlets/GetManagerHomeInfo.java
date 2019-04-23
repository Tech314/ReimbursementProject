package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import stark.project.dao.ManagerDAO;
import stark.project.dao.RequestDAO;
import stark.project.util.Requests;
import stark.project.util.Users;

/**
 * Servlet implementation class GetManagerHomeInfo
 */
@WebServlet("/GetManagerHomeInfo")
public class GetManagerHomeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetManagerHomeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Users manager = ManagerDAO.getInfo((String)session.getAttribute("user"));
		
		String uName = manager.getFname() + " " + manager.getLname();
		ArrayList<Requests> pRequests = RequestDAO.getPendingRequests();
		
		ArrayList<String> responseArr = new ArrayList<String>();
		responseArr.add(uName);
		responseArr.add("" + pRequests.size());
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper map = new ObjectMapper();
		
		out.print(map.writeValueAsString(responseArr));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
