package work.javiermantilla.fondo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpServletResponse;
import work.javiermantilla.fondo.config.TestSecurityConfig;
import work.javiermantilla.fondo.dto.AutenticationDTO;
import work.javiermantilla.fondo.services.LoginServices;
import work.javiermantilla.fondo.util.JSONUtil;


@WebMvcTest
@ContextConfiguration(classes = { LoginController.class, TestSecurityConfig.class })
class LoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private LoginServices loginServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testAutenticacion() throws Exception {
		when(this.loginServices.getTokenExisteCliente(anyString())).thenReturn("token");
		HttpServletResponse response= mock(HttpServletResponse.class);
		when(response.getHeader("Set-Cookie")).thenReturn("cookie");
		when(this.loginServices.createCookieSession(anyString(), any())).thenReturn(response);
		when(this.loginServices.getDataCookie(anyString())).thenReturn("fondo_token=token");
				
		AutenticationDTO autenticationDTO= new AutenticationDTO("13544171");
		String inputJson = JSONUtil.mapToJson(autenticationDTO);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/security/login")
				.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
