package work.javiermantilla.fondo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.TransaccionDAO;
import work.javiermantilla.fondo.entity.TransaccionEntity;

@Repository
@RequiredArgsConstructor
@Log4j2
public class TransaccionDynamoDAO implements TransaccionDAO {
	private final DynamoDBMapper dynoDbMapper;

	@Override
	public List<TransaccionEntity> findAll(String id) {
		Map<String, AttributeValue> eav = new HashMap<>();
		eav.put(":val1", new AttributeValue().withS(id));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("cliente = :val1")
				.withExpressionAttributeValues(eav);

		return dynoDbMapper.scan(TransaccionEntity.class, scanExpression);
	}

	@Override
	public List<TransaccionEntity> findAllActive(String id) {
		Map<String, AttributeValue> eav = new HashMap<>();
		eav.put(":val1", new AttributeValue().withS(id));
		eav.put(":val2", new AttributeValue().withBOOL(true));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("cliente = :val1 and activa = :val2 ").withExpressionAttributeValues(eav);

		return dynoDbMapper.scan(TransaccionEntity.class, scanExpression);
	}

	@Override
	public TransaccionEntity findById(String id) {
		DynamoDBQueryExpression<TransaccionEntity> transaccionQuery = new DynamoDBQueryExpression<>();
		log.info("Se consulta transaccion por id : {}", id);
		TransaccionEntity transaccionKey = new TransaccionEntity(id);
		transaccionQuery.setHashKeyValues(transaccionKey);
		List<TransaccionEntity> list = this.dynoDbMapper.query(TransaccionEntity.class, transaccionQuery);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public TransaccionEntity findByIdLoad(String idKey, String rangeKey) {		
		return dynoDbMapper.load(TransaccionEntity.class, idKey,rangeKey);
	}

}
