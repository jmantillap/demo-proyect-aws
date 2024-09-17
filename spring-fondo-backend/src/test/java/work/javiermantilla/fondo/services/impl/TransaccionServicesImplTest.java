package work.javiermantilla.fondo.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import work.javiermantilla.fondo.modules.transaction.dao.TransaccionDAO;
import work.javiermantilla.fondo.modules.transaction.entity.TransaccionEntity;
import work.javiermantilla.fondo.modules.transaction.services.TransaccionServices;
import work.javiermantilla.fondo.modules.transaction.services.impl.TransaccionServicesImpl;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { TransaccionServicesImpl.class })
class TransaccionServicesImplTest {

	@Autowired
    private TransaccionServices transaccionServices;

	@MockBean    
	private TransaccionDAO transaccionDAO;
	
	@Test
	void testGetTransaccionByCliente() {
		assertNotNull(this.transaccionServices.getTransaccionByCliente("1"));
	}

	@Test
	void testGetTransaccionByClienteActivas() {
		assertNotNull(this.transaccionServices.getTransaccionByClienteActivas("1"));
	}

	@Test
	void testGetTransaccionByID() {
		assertNull(this.transaccionServices.getTransaccionByID("1"));
	}

	@Test
	void testGetTransaccionFondoActivo() {
		assertNull(this.transaccionServices.getTransaccionFondoActivo("1","das"));
	}

	@Test
	void testSaveTransacciones() {
		assertTrue(this.transaccionServices.saveTransacciones(new TransaccionEntity(), new TransaccionEntity()));
	}

	@Test
	void testSaveApertura() {
		assertTrue(this.transaccionServices.saveApertura(new TransaccionEntity()));
	}

}
