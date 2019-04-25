package stark.project.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Properties;

//import javax.servlet.http.Part;

import stark.project.util.Requests;
//import stark.project.util.Users;

public class RequestDAO {
	
	private static Connection conn = null;

	
	private static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(System.getenv("jdbcUrl"),
												System.getenv("jdbcUsername"),
												System.getenv("jdbcPassword"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static ArrayList<Requests> getAllRequests(){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from requests order by request_id desc");
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static Requests getRequest(int reqId) {
		Requests req = null;
		getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement("select * from requests where request_id=? order by request_id desc");
			st.setInt(1, reqId);
			ResultSet rs = st.executeQuery();
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			
			if(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return req;
	}
	
	public static ArrayList<Requests> getPendingRequests(){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement("select * from requests where request_status='Pending' order by request_id desc");
			ResultSet rs = st.executeQuery();
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static ArrayList<Requests> getResolvedRequests(){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from requests where request_status='Resolved' order by request_id desc");
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static ArrayList<Requests> getAllRequests(int uid){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			//PreparedStatement st = conn.prepareStatement("Select e.fname,e.lname,r.request_date,r.expense_date,r.request_amt,r.request_desc,r.receipt_photo,r.request_status,r.request_decision,m.fname,m.lname from employees e,managers m,requests r where e.employee_id=r.employee_id and r.employee_id=?");
			PreparedStatement st = conn.prepareStatement("select * from requests where employee_id=?");
			st.setInt(1, uid);
			ResultSet rs = st.executeQuery();
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static ArrayList<Requests> getPendingRequests(int uid){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			//PreparedStatement st = conn.prepareStatement("Select e.fname,e.lname,r.request_date,r.expense_date,r.request_amt,r.request_desc,r.receipt_photo,r.request_status,r.request_decision,m.fname,m.lname from employees e,managers m,requests r where e.employee_id=r.employee_id and r.employee_id=?");
			PreparedStatement st = conn.prepareStatement("select * from requests where employee_id=? and request_status='Pending'");
			st.setInt(1, uid);
			ResultSet rs = st.executeQuery();
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static ArrayList<Requests> getResolvedRequests(int uid){
		ArrayList<Requests> requests = new ArrayList<Requests>();
		getConnection();
		
		try {
			//PreparedStatement st = conn.prepareStatement("Select e.fname,e.lname,r.request_date,r.expense_date,r.request_amt,r.request_desc,r.receipt_photo,r.request_status,r.request_decision,m.fname,m.lname from employees e,managers m,requests r where e.employee_id=r.employee_id and r.employee_id=?");
			PreparedStatement st = conn.prepareStatement("select * from requests where employee_id=? and request_status='Resolved'");
			st.setInt(1, uid);
			ResultSet rs = st.executeQuery();
			ResultSet emp = null;
			ResultSet man = null;
			PreparedStatement es = conn.prepareStatement("select fname,lname,email from employees where employee_id=?");
			PreparedStatement ms = conn.prepareStatement("select fname,lname from managers where manager_id=?");
			Requests req = null;
			
			while(rs.next()) {
				req = new Requests(
						rs.getInt("request_id"),
						rs.getInt("employee_id"),
						rs.getInt("manager_id"),
						rs.getDate("request_date"),
						rs.getDate("expense_date"),
						rs.getDouble("request_amt"),
						rs.getString("request_desc"),
						rs.getString("request_status"),
						rs.getString("request_decision"));
				//req.setPic(rs.getBlob("receipt_photo"));
				es.setInt(1,req.getEmpId());
				ms.setInt(1, req.getManId());
				emp = es.executeQuery();
				man = ms.executeQuery();
				if(emp.next()) {
					req.setEmpFName(emp.getString("fname"));
					req.setEmpLName(emp.getString("lname"));
					req.setEmail(emp.getString("email"));
				}
				if(man.next()) {
					req.setManFName(man.getString("fname"));
					req.setManLName(man.getString("lname"));
				}
				
				requests.add(req);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return requests;
	}
	
	public static void insertRequest(Requests request) {
		getConnection();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from requests");
			rs.next();
			int reqId = rs.getInt(1);
			
			PreparedStatement ps = conn.prepareStatement("insert into requests "
					+ "columns (request_id,employee_id,request_date,expense_date,request_amt,request_desc,request_status,receipt_photo)"
					+ "values (?,?,sysdate,?,?,?,?,?)");
			ps.setInt(1, reqId);
			ps.setInt(2, request.getEmpId());
			ps.setDate(3, request.getExpDate());
			ps.setDouble(4, request.getReqAmt());
			ps.setString(5, request.getReqDesc());
			ps.setString(6, request.getReqStatus());
			ps.setBinaryStream(7, request.getExpReceipt().getInputStream(), (int)request.getExpReceipt().getSize());
			
			System.out.println(ps.executeUpdate());
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void resolveRequest(Requests request) {
		getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("update requests set request_decision=?, manager_id=?,request_status='Resolved' where request_id=?");
			ps.setString(1, request.getReqDecision());
			ps.setInt(2, request.getManId());
			ps.setInt(3, request.getReqId());
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Blob getReceipt(int reqId) {
		getConnection();
		Blob pic = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement("select receipt_photo from requests where request_id=?");
			ps.setInt(1, reqId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				pic = rs.getBlob("receipt_photo");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return pic;
	}
}
