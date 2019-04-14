package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import stark.project.dao.RequestDAO;
import stark.project.util.Requests;

/**
 * Servlet implementation class GetAllRequests
 */
@WebServlet("/GetAllRequests")
public class GetAllRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllRequests() {
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
		//ObjectMapper map = new ObjectMapper();
		
		ArrayList<Requests> requests = RequestDAO.getAllRequests();
		
		//System.out.println(map.writeValueAsString(requests));
		//out.print(map.writeValueAsString(requests));
		for(Requests req: requests) {
			if(req.getReqStatus().equals("Pending")) {
				out.println("<tr>" +
					"<td>" + req.getEmpFName() + " " + req.getEmpLName() + "</td>" +
					"<td>" + req.getReqDate() + "</td>" +
					"<td>" + req.getExpDate() + "</td>" +
					"<td>" + req.getReqAmt() + "</td>" +
					"<td>" + req.getReqDesc() + "</td>" + 
					"<td>" + ""/*requestArr[i].exp.Receipt*/ + "</td>" +
					"<td>" + req.getReqStatus() + "</td>" +
					"<td>" + "<select onchange='resolveReq(this.value," + req.getReqId() + "," + req.getEmpId() + ")'><option>--Resolve--</option><option value='Approve'>Approve</option><option value='Reject'>Reject</option></select>" + "</td>" +
					"<td>" + "" + "</td></tr>");
			}
			else {
				out.println("<tr>" +
						"<td>" + req.getEmpFName() + " " + req.getEmpLName() + "</td>" +
						"<td>" + req.getReqDate() + "</td>" +
						"<td>" + req.getExpDate() + "</td>" +
						"<td>" + req.getReqAmt() + "</td>" +
						"<td>" + req.getReqDesc() + "</td>" + 
						"<td>" + ""/*requestArr[i].exp.Receipt*/ + "</td>" +
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
