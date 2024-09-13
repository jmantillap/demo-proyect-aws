package work.javiermantilla.fondo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.ClienteDAO;
import work.javiermantilla.fondo.entity.ClienteEntity;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ClienteDynamoDAOImpl implements ClienteDAO {

	private final DynamoDBMapper dynoDbMapper;

	@Override
	public ClienteEntity getCliente(String id) {
		DynamoDBQueryExpression<ClienteEntity> clienteQuery = new DynamoDBQueryExpression<>();		
		log.info("Se consulta cliente por id : {}", id);
		ClienteEntity clienteKey = new ClienteEntity(id);
		clienteQuery.setHashKeyValues(clienteKey);		
		List<ClienteEntity> list=this.dynoDbMapper.query(ClienteEntity.class, clienteQuery);
		return list.isEmpty() ? null : list.get(0); 
	}

	@Override
	public ClienteEntity getClienteById(String  hashKey, String rangeKey) {
		return dynoDbMapper.load(ClienteEntity.class, hashKey,rangeKey);
	}

}
