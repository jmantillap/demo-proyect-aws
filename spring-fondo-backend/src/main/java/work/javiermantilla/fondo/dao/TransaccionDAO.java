package work.javiermantilla.fondo.dao;

import java.util.List;


import work.javiermantilla.fondo.entity.TransaccionEntity;

public interface TransaccionDAO {
	List<TransaccionEntity> findAll(String cliente);
	List<TransaccionEntity> findAllActive(String cliente);
	TransaccionEntity findById(String id);
	TransaccionEntity findByIdLoad(String idKey, String rangeKey);
	TransaccionEntity findActiveFondo(String cliente,String fondo);
	boolean saveOrUpdate(TransaccionEntity transaccion);
	
	
}
