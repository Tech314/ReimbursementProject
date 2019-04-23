package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import stark.project.dao.EmployeeDAO;
import stark.project.dao.RequestDAO;
import stark.project.util.Requests;
import stark.project.util.Users;

/**
 * Servlet implementation class GetPendingEmpRequests
 */
@WebServlet("/GetPendingEmpRequests")
public class GetPendingEmpRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPendingEmpRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		Users user = EmployeeDAO.getInfo((String)session.getAttribute("user"));
		
		ArrayList<Requests> requests = RequestDAO.getPendingRequests(Integer.parseInt(user.getId()));
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMinimumFractionDigits(2);
		df.setRoundingMode(RoundingMode.FLOOR);
		for(Requests req: requests) {
			if(req.getReqStatus().equals("Pending")) {
				out.println("<tr>" +
					"<td>" + req.getReqDate() + "</td>" +
					"<td>" + req.getExpDate() + "</td>" +
					"<td>$" + df.format(req.getReqAmt()) + "</td>" +
					"<td>" + req.getReqDesc() + "</td>" + 
					"<td>" + req.getReqStatus() + "</td>" +
					"<td>" + "" + "</td>" +
					"<td>" + "" + "</td></tr>");
			}
			else {
				out.println("<tr>" +
						"<td>" + req.getReqDate() + "</td>" +
						"<td>" + req.getExpDate() + "</td>" +
						"<td>$" + df.format(req.getReqAmt()) + "</td>" +
						"<td>" + req.getReqDesc() + "</td>" + 
						"<td>" + req.getReqStatus() + "</td>" +
						"<td>" + req.getReqDecision() + "</td>" +
						"<td>" + req.getManFName() + " " + req.getManLName() + "</td></tr>");
			}
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
