package work.javiermantilla.fondo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import work.javiermantilla.fondo.basic.utils.JSONUtil;
import work.javiermantilla.fondo.config.TestSecurityConfig;
import work.javiermantilla.fondo.modules.fund.controller.FondoController;
import work.javiermantilla.fondo.modules.fund.services.FondosServices;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoAperturaDTO;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoDTO;

@WebMvcTest
@ContextConfiguration(classes = { FondoController.class, TestSecurityConfig.class })
@Log4j2
class FondoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private FondosServices fondosServices;

	@BeforeEach
	protected void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void testGetFondos() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/fondo")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testGetFondosActivosCliente() throws Exception{
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/fondo/{id}/inscritos","13544171")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testGetFondosNoInscritosCliente() throws Exception{
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/fondo/{id}/no-inscritos","13544171")				
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();		
		log.info(mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testSetSuscribir() throws Exception{
		MovimientoAperturaDTO movimientoAperturaDTO= new MovimientoAperturaDTO();
		String inputJson = JSONUtil.mapToJson(movimientoAperturaDTO);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/fondo/apertura")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
		
		movimientoAperturaDTO.setFondo("10");
		movimientoAperturaDTO.setMonto(20);
		inputJson = JSONUtil.mapToJson(movimientoAperturaDTO);
		mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/fondo/apertura")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		
	}

	@Test
	void testSetCancelar() throws Exception{
		MovimientoDTO movimientoDTO= new MovimientoDTO();
		String inputJson = JSONUtil.mapToJson(movimientoDTO);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/fondo/cancelar")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();		
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
		
		movimientoDTO.setFondo("10");		
		inputJson = JSONUtil.mapToJson(movimientoDTO);
		mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/fondo/cancelar")
						.content(inputJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

}
