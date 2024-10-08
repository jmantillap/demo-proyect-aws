package work.javiermantilla.fondo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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

import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.config.TestSecurityConfig;
import work.javiermantilla.fondo.modules.client.controller.ClienteController;
import work.javiermantilla.fondo.modules.client.services.ClienteServices;


@WebMvcTest
@ContextConfiguration(classes = { ClienteController.class, TestSecurityConfig.class })
@Log4j2
class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private ClienteServices clienteServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testGetCliente() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/cliente/{id}","13544171")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testGetClienteById() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/cliente/{id}/{email}","13544171","email")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
