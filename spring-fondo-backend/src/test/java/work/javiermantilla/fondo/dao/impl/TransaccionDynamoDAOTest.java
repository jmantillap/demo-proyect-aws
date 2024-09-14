package work.javiermantilla.fondo.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import lombok.extern.log4j.Log4j2;

import work.javiermantilla.fondo.dao.TransaccionDAO;

import work.javiermantilla.fondo.entity.TransaccionEntity;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = { TransaccionDynamoDAO.class })
@Log4j2
class TransaccionDynamoDAOTest {

	@Autowired
	private TransaccionDAO transaccionDAO;

	@MockBean
	private DynamoDBMapper dynoDbMapper;

	@Test
	void testFindAll() {
		assertNull(this.transaccionDAO.findAll("13544171"));
	}

	@Test
	void testFindAllActive() {
		assertNull(this.transaccionDAO.findAllActive("13544171"));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testFindById() {

		PaginatedQueryList<Object> list = mock(PaginatedQueryList.class);
		when(this.dynoDbMapper.query(any(), any())).thenReturn(list);
		assertNull(this.transaccionDAO.findById("13544171"));

		List<TransaccionEntity> list1 = new ArrayList<>();
		when(dynoDbMapper.query(eq(TransaccionEntity.class), any(DynamoDBQueryExpression.class))).thenReturn(
				mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));

		assertNull(this.transaccionDAO.findById("13544171"));

		list1.add(new TransaccionEntity());
		when(dynoDbMapper.query(eq(TransaccionEntity.class), any(DynamoDBQueryExpression.class))).thenReturn(
				mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));

		assertNotNull(this.transaccionDAO.findById("13544171"));
	}

	@Test
	void testFindByIdLoad() {
		assertNull(this.transaccionDAO.findByIdLoad("demo", "demo"));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testFindActiveFondo() {
		PaginatedScanList<Object> list = mock(PaginatedScanList.class);
		when(dynoDbMapper.scan(any(), any())).thenReturn(list);
		assertNull(this.transaccionDAO.findActiveFondo("demo", "demo"));

		List<TransaccionEntity> list1 = new ArrayList<>();
		when(dynoDbMapper.scan(eq(TransaccionEntity.class), any(DynamoDBScanExpression.class))).thenReturn(
				mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));
		assertNull(this.transaccionDAO.findActiveFondo("demo", "demo"));

		list1.add(new TransaccionEntity());
		when(dynoDbMapper.scan(eq(TransaccionEntity.class), any(DynamoDBScanExpression.class))).thenReturn(
				mock(PaginatedScanList.class, withSettings().defaultAnswer(new ForwardsInvocations(list1))));
		assertNotNull(this.transaccionDAO.findActiveFondo("demo", "demo"));
	}

	@Test
	void testSaveOrUpdate() {
		assertTrue(this.transaccionDAO.saveOrUpdate(new TransaccionEntity()));
	}

}
