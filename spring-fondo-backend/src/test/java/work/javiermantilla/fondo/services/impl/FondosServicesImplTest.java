package work.javiermantilla.fondo.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import work.javiermantilla.fondo.basic.dto.UserContextSessionDTO;
import work.javiermantilla.fondo.basic.security.ContextSession;
import work.javiermantilla.fondo.basic.utils.ETipoTransaccion;
import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;
import work.javiermantilla.fondo.modules.client.services.ClienteServices;
import work.javiermantilla.fondo.modules.email.services.EmailServices;
import work.javiermantilla.fondo.modules.fund.dao.FondoDAO;
import work.javiermantilla.fondo.modules.fund.entity.FondoEntity;
import work.javiermantilla.fondo.modules.fund.services.FondosServices;
import work.javiermantilla.fondo.modules.fund.services.impl.FondosServicesImpl;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoAperturaDTO;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoDTO;
import work.javiermantilla.fondo.modules.transaction.entity.TransaccionEntity;
import work.javiermantilla.fondo.modules.transaction.services.TransaccionServices;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { FondosServicesImpl.class })
class FondosServicesImplTest {

	@Autowired
    private FondosServices fondosServices;

	@MockBean    
	private FondoDAO fondoDAO;
	@MockBean
	private TransaccionServices transaccionServices;
	@MockBean
	private ClienteServices clienteServices;
	@MockBean
	private ContextSession contextSession;
	@MockBean
	private EmailServices emailServices;
	
	@Test
	void testGetFondos() {
		assertTrue(this.fondosServices.getFondos().isEmpty());
	}

	@Test
	void testGetFondosActivos() {
		assertTrue(this.fondosServices.getFondosActivos("10").isEmpty());
	}

	@Test
	void testGetFondosInactivos() {
		assertTrue(this.fondosServices.getFondosInactivos("10").isEmpty());
	}

	@Test
	void testMovimientoFondo() {
		when(contextSession.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre","email", new Date()));
		assertNotNull(this.fondosServices.movimientoFondo(new MovimientoAperturaDTO("10",10F), ETipoTransaccion.APERTURA));
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.fondosServices.movimientoFondo(new MovimientoDTO("10"), ETipoTransaccion.CANCELACION);
		});
		assertFalse(exception.getMessage().isEmpty());
		
		TransaccionEntity transaccionFondo = new TransaccionEntity("1", "1", "1", 10000, "dasdass", false, "C"); 
		when(this.transaccionServices.getTransaccionFondoActivo(anyString(), anyString())).thenReturn(transaccionFondo);
		
		ClienteEntity cliente = new ClienteEntity("id", "emal","nombre","10000");
		when(this.clienteServices.getClienteById(anyString(), anyString())).thenReturn(cliente);
		assertNotNull(this.fondosServices.movimientoFondo(new MovimientoDTO("10"), ETipoTransaccion.CANCELACION));
		
		List<FondoEntity> list = List.of(new FondoEntity("1"),new FondoEntity("2")); 
		when(this.fondoDAO.findAll()).thenReturn(list);
		MovimientoDTO movi=new MovimientoDTO();
		movi.setFondo("1");		
		assertNotNull(this.fondosServices.movimientoFondo(movi, ETipoTransaccion.CANCELACION));
		
		
	}

}
