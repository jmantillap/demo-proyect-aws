package work.javiermantilla.fondo.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import static org.mockito.Mockito.withSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.modules.client.dao.ClienteDAO;
import work.javiermantilla.fondo.modules.client.dao.impl.ClienteDynamoDAOImpl;
import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { ClienteDynamoDAOImpl.class })
@Log4j2
class ClienteDynamoDAOImplTest {

	@Autowired
    private ClienteDAO clienteDAO ;

	@MockBean    
	private  DynamoDBMapper dynoDbMapper;
	
	
	@SuppressWarnings("unchecked")
	@Test
	void testGetCliente() {
		PaginatedQueryList<Object> list = mock(PaginatedQueryList.class);  
		when(this.dynoDbMapper.query(any(), any())).thenReturn(list);		
		assertNull(this.clienteDAO.getCliente("13544171"));
		
		List<ClienteEntity> list1 = new ArrayList<>();		
		when(dynoDbMapper.query(eq(ClienteEntity.class), any(DynamoDBQueryExpression.class)))
		    .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));
		
		assertNull(this.clienteDAO.getCliente("13544171"));
		
		list1.add(new ClienteEntity());
		when(dynoDbMapper.query(eq(ClienteEntity.class), any(DynamoDBQueryExpression.class)))
	    .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));
		
		assertNotNull(this.clienteDAO.getCliente("13544171"));
		
	}

	@Test
	void testGetClienteById() {
		assertNull(clienteDAO.getClienteById("13544171", "email"));
	}

	@Test
	void testSaveOrUpdate() {
		assertTrue(this.clienteDAO.saveOrUpdate(new ClienteEntity()));
	}

}
