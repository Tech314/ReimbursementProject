package stark.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditInfoLanding
 */
@WebServlet("/EditInfoLanding")
public class EditInfoLanding extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditInfoLanding() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		if(session.getAttribute("userType").equals("Employee")) {
			if((boolean) request.getAttribute("success")) {
				out.println("Successfully edited information");
				//out.println("<button type='button'><a href='Employee/EmployeeInfo.html'>Back to Info</a></button>");
			}
			else {
				out.println("Error editing information");
			//	out.println("<button type='button'><a href='Employee/EmployeeInfo.html'>Back to Info</a></button>");
			}
		}
		
		else if(session.getAttribute("userType").equals("Manager")) {
			if((boolean) request.getAttribute("success")) {
				out.println("Successfully edited information");
			//	out.println("<button type='button'><a href='Manager/ManagerInfo.html'>Back to Info</a></button>");
			}
			else {
				out.println("Error editing information");
			//	out.println("<button type='button'><a href='Manager/Manager.html'>Back to Info</a></button>");
			}
		}
	//	out.println("</body></html>");
		out.println("You can safely navigate back to the previous page");
		
	//	RequestDispatcher rd = request.getRequestDispatcher("/infolanding.jsp");
	//	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
