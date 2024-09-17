package work.javiermantilla.fondo.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

import work.javiermantilla.fondo.modules.client.dao.ClienteDAO;
import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;
import work.javiermantilla.fondo.modules.client.services.ClienteServices;
import work.javiermantilla.fondo.modules.client.services.impl.ClienteServicesImpl;



@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { ClienteServicesImpl.class })
class ClienteServicesImplTest {
	
		
	@Autowired
    private ClienteServices clienteServices;

	@MockBean    
	private ClienteDAO clienteDAO;
	
	@Test
	void testGetCliente() {
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.clienteServices.getCliente("1354");
		});
		assertFalse(exception.getMessage().isEmpty());
		
		when(this.clienteDAO.getCliente(anyString())).thenReturn(new ClienteEntity());		
		assertNotNull(this.clienteServices.getCliente("1354"));
	}

	@Test
	void testGetClienteByIdDTO() {
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			this.clienteServices.getClienteByIdDTO("1354","otro");
		});
		assertFalse(exception.getMessage().isEmpty());
		
		when(clienteDAO.getClienteById(anyString(),anyString())).thenReturn(new ClienteEntity());		
		assertNotNull(this.clienteServices.getClienteByIdDTO("1354","otro"));
	}

	@Test
	void testSaveCliente() {
		assertFalse(this.clienteServices.saveCliente(new ClienteEntity()));
	}

}
