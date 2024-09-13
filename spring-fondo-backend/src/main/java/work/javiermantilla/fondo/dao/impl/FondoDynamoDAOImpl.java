package work.javiermantilla.fondo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.FondoDAO;
import work.javiermantilla.fondo.entity.FondoEntity;


@Repository
@Log4j2
@RequiredArgsConstructor
public class FondoDynamoDAOImpl implements FondoDAO {
	
	private final DynamoDBMapper dynoDbMapper;

	@Override
	public List<FondoEntity> findAll() {		
		return dynoDbMapper.scan(FondoEntity.class, new DynamoDBScanExpression());		
	}

	@Override
	public FondoEntity findByID(String id) {
		
		return null;
	}
	
	

}
