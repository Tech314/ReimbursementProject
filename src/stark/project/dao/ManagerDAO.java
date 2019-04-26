package stark.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import stark.project.util.ConnectionFactory;
import stark.project.util.Users;

public class ManagerDAO implements Login {
	
	private static Connection conn = null;
	
	public boolean validate(String name, String pass) {
		boolean status = false; 
		
		try(Connection conn = ConnectionFactory.getConnection()){  
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
		return status;  
	}
	
	public static Users getInfo(String uname) {
		Users u = new Users();
		
		try(Connection conn = ConnectionFactory.getConnection()){  
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Managers where user_name=?");  
			ps.setString(1,uname);   
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u.setId("" + rs.getInt("employee_id"));
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
		
		return u;
	}
	
	public static Users getInfo(int eid) {
		Users u = new Users();
		
		try(Connection conn = ConnectionFactory.getConnection()){  
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Managers where employee_id=?");  
			ps.setInt(1,eid);   
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u.setId("" + rs.getInt("employee_id"));
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
		
		return u;
	}
	
	public static void editManInf(Users emp) {
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement("Update managers "
					+ "set user_name=?, email=?, user_pass=?"
					+ "where employee_id=?");
			ps.setInt(4, Integer.parseInt(emp.getId()));
			ps.setString(1, emp.getUname());
			ps.setString(3, emp.getPass());
			ps.setString(2, emp.getEmail());
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean resetPass(int eid, String newPass) {
		boolean reset = false;
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("update managers set user_pass=? where employee_id=?");
			ps.setString(1, newPass);
			ps.setInt(2, eid);
			
			if(ps.executeUpdate() != 0) {
				reset = true;
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
		return reset;
	}
}
