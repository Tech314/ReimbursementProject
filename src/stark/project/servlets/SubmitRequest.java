package stark.project.servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import stark.project.dao.EmployeeDAO;
import stark.project.dao.RequestDAO;
import stark.project.util.Requests;
import stark.project.util.Users;

/**
 * Servlet implementation class SubmitRequest
 */
@WebServlet(name="SubmitRequest", urlPatterns="/SubmitRequest")
@MultipartConfig
public class SubmitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Users user = EmployeeDAO.getInfo((String)session.getAttribute("user"));
		
		Requests req = new Requests();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date eDate = null;
		Date expDate = null;
		
		try {
			eDate = formatter.parse(request.getParameter("expDate"));
			expDate = new java.sql.Date(eDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double expAmt = Double.parseDouble(request.getParameter("expAmt"));
		String expDesc = request.getParameter("expDesc");
		Part pic = request.getPart("expPhoto");
		
		req.setEmpId(Integer.parseInt(user.getId()));
		req.setExpDate(expDate);
		req.setReqAmt(expAmt);
		req.setReqDesc(expDesc);
		req.setReqStatus("Pending");
		req.setExpReceipt(pic);
		
		RequestDAO.insertRequest(req);
		
		response.sendRedirect("Employees/EmployeeRequests.html");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
