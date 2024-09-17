package work.javiermantilla.fondo.modules.login.services;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.fondo.basic.dto.UserContextSessionDTO;



public interface LoginServices {
	
	//String getToken(String userName);	
	HttpServletResponse createCookieSession(String token, HttpServletResponse response);
	UserContextSessionDTO getUserDataCookie(String cookieSession);
	String getDataCookie(String cookieSession);
	String getTokenExisteCliente(String id);
	
}
