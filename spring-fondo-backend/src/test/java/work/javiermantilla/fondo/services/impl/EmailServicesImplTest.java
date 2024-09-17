package work.javiermantilla.fondo.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import software.amazon.awssdk.services.ses.SesClient;
import work.javiermantilla.fondo.basic.dto.UserContextSessionDTO;
import work.javiermantilla.fondo.basic.security.ContextSession;
import work.javiermantilla.fondo.modules.email.services.EmailServices;
import work.javiermantilla.fondo.modules.email.services.impl.EmailServicesImpl;
import work.javiermantilla.fondo.modules.fund.dto.FondoDTO;




@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { EmailServicesImpl.class })
class EmailServicesImplTest {

	@Autowired
    private EmailServices emailServices;

	@MockBean    
	private SesClient sesClient;
	@MockBean
	private ContextSession contextSession;
	
	
	@Test
	void testSendEmail() {
		when(contextSession.getContextSessionThread()).thenReturn(new UserContextSessionDTO("10", "nombre","email", new Date()));
		assertNotNull(this.emailServices.sendEmail(new FondoDTO("10","codigo","categoria", 500000F, "nombre")));
	}

}
