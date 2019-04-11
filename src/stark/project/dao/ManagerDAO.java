package stark.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import stark.project.util.Users;

public class ManagerDAO {
	
	private static Connection conn = null;
	
	private static void getConnection() {		
		try{  
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dbURL,"Tech314","oracle");  
		         
		}
		catch(SQLException e){
			e.printStackTrace();
		}  
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean validate(String name, String pass) {
		boolean status = false; 
		getConnection();
		
		try{  
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Managers where user_name=? and user_pass=?");  
			ps.setString(1,name);  
			ps.setString(2,pass);  
			
			ResultSet rs = ps.executeQuery();  
			status = rs.next();  
		          
		}
		catch(SQLException e){
			e.printStackTrace();
		} 
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return status;  
	}
	
	public static Users getInfo(String uname) {
		Users u = new Users();
		
		try{  
			getConnection(); 
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Managers where user_name=?");  
			ps.setString(1,uname);   
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u.setId("" + rs.getInt("Manager_ID"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setUname(rs.getString("user_name"));
				u.setPass(rs.getString("user_pass"));
				u.setEmail(rs.getString("email"));
			}
			
		          
		}
		catch(SQLException e){
			e.printStackTrace();
		} 
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return u;
	}
	
	public static void editManInf(Users emp) {
		try {
			getConnection();
			
			PreparedStatement ps = conn.prepareStatement("Update table managers"
					+ "set user_name=? and email=? and user_pass=?"
					+ "where manager_id=?");
			ps.setInt(4, Integer.parseInt(emp.getId()));
			ps.setString(1, emp.getUname());
			ps.setString(3, emp.getPass());
			ps.setString(2, emp.getEmail());
			
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
}
