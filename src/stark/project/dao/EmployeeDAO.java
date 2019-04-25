package stark.project.dao;

import java.io.IOException;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Properties;
import java.util.Properties;

import stark.project.util.Users;

public class EmployeeDAO {
	
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
	
	public static boolean validate(String name,String pass) {
		boolean status = false;  
		
		try{    
			getConnection();
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Employees where user_name=? and user_pass=?");  
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
			
			ps = conn.prepareStatement("select * from Employees where user_name=?");  
			ps.setString(1,uname);   
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u.setId("" + rs.getInt("Employee_Id"));
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
			
			ps = conn.prepareStatement("select * from Employees where employee_id=?");  
			ps.setInt(1,eid);   
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u.setId("" + rs.getInt("Employee_Id"));
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
	
	public static long insertEmployee(Users emp) {
		long records = 0;
		try {
			getConnection();
			
			int id = (int)(1000 + Math.random()*4000);
			PreparedStatement idCheck = conn.prepareStatement("Select * from employees where employee_id=?");
			idCheck.setInt(1, id);
			ResultSet rs = idCheck.executeQuery();
			
			while(rs.next()) {
				id = (int)(1000 + Math.random()*4000);
				idCheck.setInt(1, id);
				rs.close();
				rs = idCheck.executeQuery();
			}
			rs.close();
			
			
			PreparedStatement ps = conn.prepareStatement("Insert into employees"
					+ "(employee_id,fname,lname,user_name,user_pass,email)"
					+ "values (?,?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, emp.getFname());
			ps.setString(3, emp.getLname());
			ps.setString(4, emp.getUname());
			ps.setString(5, emp.getPass());
			ps.setString(6, emp.getEmail());
			
			records = ps.executeUpdate();
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
		
		return records;
	}
	
	public static long editEmpInf(Users emp) {
		long records = 0;
		try {
			getConnection();
			
			PreparedStatement ps = conn.prepareStatement("Update employees "
					+ "set user_name=?, email=?, user_pass=? "
					+ "where employee_id=?");
			ps.setInt(4, Integer.parseInt(emp.getId()));
			ps.setString(1, emp.getUname());
			ps.setString(3, emp.getPass());
			ps.setString(2, emp.getEmail());
			
			records = ps.executeUpdate();
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
		
		return records;
	}
	
	public static List<Users> getEmployeeList(){
		List<Users> users = new ArrayList<Users>();
		getConnection();
		
		try{ 
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from Employees");  
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				users.add(new Users(
						("" + rs.getInt("Employee_Id")),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getString("user_name"),
						rs.getString("user_pass"),
						rs.getString("email")));
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
		
		return users;
	}
	
	public static boolean resetPass(int eid, String newPass) {
		boolean reset = false;
		getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement("update employees set user_pass=? where employee_id=?");
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
