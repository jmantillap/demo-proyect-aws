package work.javiermantilla.fondo.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.fondo.dto.ClienteDTO;
import work.javiermantilla.fondo.security.JwtUtil;
import work.javiermantilla.fondo.services.ClienteServices;
import work.javiermantilla.fondo.services.LoginServices;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { LoginServicesImpl.class })
class LoginServicesImplTest {

	@Autowired
    private LoginServices loginServices;

	@MockBean    
    private JwtUtil jwtUtil;
	@MockBean
	private ClienteServices clienteServices;
	
	private String cadena="eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIxMzU0NDE3MSIsIm5hbWUiOiJKYXZpZXIgTWFudGlsbGEiLCJlbWFpbCI6ImptYW50aWxsYXBAZ21haWwuY29tIiwiaXNzIjoiMTM1NDQxNzEiLCJzdWIiOiJKYXZpZXIgTWFudGlsbGEiLCJpYXQiOjE3MjYyMzg1MTMsImV4cCI6MTcyNjI4MTcxM30.B3eYhpfjxoi3sZwF9M5zv777QsqxDyoAIhZ83wbb7uo";
	private String cadenaDos="fondo_token=eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIxMzU0NDE3MSIsIm5hbWUiOiJKYXZpZXIgTWFudGlsbGEiLCJlbWFpbCI6ImptYW50aWxsYXBAZ21haWwuY29tIiwiaXNzIjoiMTM1NDQxNzEiLCJzdWIiOiJKYXZpZXIgTWFudGlsbGEiLCJpYXQiOjE3MjYyMzg1MTMsImV4cCI6MTcyNjI4MTcxM30.B3eYhpfjxoi3sZwF9M5zv777QsqxDyoAIhZ83wbb7uo; Max-Age=960; Expires=Mon, 02 Sep 2024 05:56:16 GMT; Domain=localhost; Path=/; Secure; HttpOnly\r\n"
			+ "";
	private String cadenaTres="_token=eyJhbGciOiJIUzI1NiJ9.eyJpZFVzZXIiOiIxMzU0NDE3MSIsIm5hbWUiOiJKYXZpZXIgTWFudGlsbGEiLCJlbWFpbCI6ImptYW50aWxsYXBAZ21haWwuY29tIiwiaXNzIjoiMTM1NDQxNzEiLCJzdWIiOiJKYXZpZXIgTWFudGlsbGEiLCJpYXQiOjE3MjYyMzg1MTMsImV4cCI6MTcyNjI4MTcxM30.B3eYhpfjxoi3sZwF9M5zv777QsqxDyoAIhZ83wbb7uo; Max-Age=960; Expires=Mon, 02 Sep 2024 05:56:16 GMT; Domain=localhost; Path=/; Secure; HttpOnly\r\n"
			+ "";
	
	@Test
	void testGetTokenExisteCliente() {
		ClienteDTO cliente= new ClienteDTO("13544171", "jmantillap@gmail.com", "nombre", 0);
		when(this.clienteServices.getCliente(anyString())).thenReturn(cliente);		
		assertNull(this.loginServices.getTokenExisteCliente(cadena));
		
		when(jwtUtil.createJWT(anyString(), anyString(),anyString())).thenReturn("token");
		assertNotNull(this.loginServices.getTokenExisteCliente(cadena));
						
	}

	@Test
	void testCreateCookieSession() {
		HttpServletResponse response = mock(HttpServletResponse.class);
    	assertNotNull(this.loginServices.createCookieSession(cadena, response));
    	
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		this.loginServices.createCookieSession(cadena, null);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		exception = assertThrows(ResponseStatusException.class, () -> {
    		this.loginServices.createCookieSession("", response);
		});
		assertFalse(exception.getMessage().isEmpty());
	}

	@Test
	void testGetUserDataCookie() {
		assertNull(this.loginServices.getUserDataCookie(cadenaDos));
	}

	@Test
	void testGetDataCookie() {
		assertNotNull(this.loginServices.getDataCookie(cadenaDos));		
		assertNull(this.loginServices.getDataCookie(cadenaTres));
	}

}
