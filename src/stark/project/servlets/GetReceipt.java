package stark.project.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import stark.project.dao.RequestDAO;

/**
 * Servlet implementation class GetReceipt
 */
@WebServlet("/Managers/GetReceipt")
public class GetReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetReceipt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("image/png");
		
		try {
			Blob receipt = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			Connection conn = DriverManager.getConnection(dbURL,"Tech314","oracle");
			
			PreparedStatement ps = conn.prepareStatement("select receipt_photo from requests where request_id=?");
			ps.setInt(1, Integer.parseInt(request.getParameter("rid")));
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				receipt = rs.getBlob("receipt_photo");
			}
			if(receipt != null) {
				response.setContentLength((int) receipt.length());
		        InputStream is = receipt.getBinaryStream();
		        OutputStream os = response.getOutputStream();
		        byte buf[] = new byte[(int) receipt.length()];
		        is.read(buf);
		        os.write(buf);
		        os.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
