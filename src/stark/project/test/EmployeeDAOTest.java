package stark.project.test;

import static org.junit.Assert.*;

//import java.sql.Connection;

//import org.junit.BeforeClass;
import org.junit.Test;

import stark.project.dao.EmployeeDAO;
//import stark.project.util.ConnectionFactory;
import stark.project.util.Users;

public class EmployeeDAOTest {
	
	@Test
	public void testInsert() {
		Users emp = new Users("1","Test","Person","tperson","password1","test@email.com");
		long value = EmployeeDAO.insertEmployee(emp);
		assertEquals(1,value);
	}

}
