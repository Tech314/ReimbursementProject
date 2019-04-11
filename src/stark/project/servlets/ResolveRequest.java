package stark.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
