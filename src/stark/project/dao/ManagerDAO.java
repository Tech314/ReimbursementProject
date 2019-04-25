package stark.project.dao;

import java.io.IOException;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Properties;
import java.util.Properties;

import stark.project.util.Users;

public class ManagerDAO {
	
	private static Connection conn = null;
private static final Properties props = getJdbcProperties();
	
	private static Properties getJdbcProperties() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return props;
	}
	
	private static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(props.getProperty("jdbcUrl"),
					props.getProperty("jdbcUsername"),
					props.getProperty("jdbcPassword"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
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
	
	public static Users getInfo(int eid) {
		Users u = new Users();
		
		try{  
			getConnection(); 
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Managers where manager_id=?");  
			ps.setInt(1,eid);   
			
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
			
			PreparedStatement ps = conn.prepareStatement("Update managers "
					+ "set user_name=?, email=?, user_pass=?"
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
	
	public static boolean resetPass(int eid, String newPass) {
		boolean reset = false;
		getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("update managers set user_pass=? where manager_id=?");
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
