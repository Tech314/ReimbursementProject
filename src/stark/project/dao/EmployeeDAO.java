package stark.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import stark.project.util.ConnectionFactory;
import stark.project.util.Users;

public class EmployeeDAO implements Login {
	
	private static Connection conn = null;
	
	public boolean validate(String name,String pass) {
		boolean status = false;  
		
		try(Connection conn = ConnectionFactory.getConnection()){    
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from tech314.Employees where user_name=? and user_pass=?");  
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
			
			ps = conn.prepareStatement("select * from tech314.Employees where user_name=?");  
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
		
		return u;
	}
	
	public static Users getInfo(int eid) {
		Users u = new Users();
		
		try(Connection conn = ConnectionFactory.getConnection()){  
			
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from tech314.Employees where employee_id=?");  
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
		
		return u;
	}
	
	public static long insertEmployee(Users emp) {
		long records = 0;
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			int id = (int)(1000 + Math.random()*4000);
			PreparedStatement idCheck = conn.prepareStatement("Select * from tech314.employees where employee_id=?");
			idCheck.setInt(1, id);
			ResultSet rs = idCheck.executeQuery();
			
			while(rs.next()) {
				id = (int)(1000 + Math.random()*4000);
				idCheck.setInt(1, id);
				rs.close();
				rs = idCheck.executeQuery();
			}
			rs.close();
			
			
			PreparedStatement ps = conn.prepareStatement("Insert into tech314.employees"
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
		
		return records;
	}
	
	public static long editEmpInf(Users emp) {
		long records = 0;
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement("Update tech314.employees "
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
		
		return records;
	}
	
	public static List<Users> getEmployeeList(){
		List<Users> users = new ArrayList<Users>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
		      
			PreparedStatement ps = null;
			
			ps = conn.prepareStatement("select * from tech314.Employees");  
			
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
		
		return users;
	}
	
	public static boolean resetPass(int eid, String newPass) {
		boolean reset = false;
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("update tech314.employees set user_pass=? where employee_id=?");
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
