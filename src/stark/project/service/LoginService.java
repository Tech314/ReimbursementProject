package stark.project.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
	void attemptValidation(HttpServletRequest request, HttpServletResponse response) throws IOException; 
}
