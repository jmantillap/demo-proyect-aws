package work.javiermantilla.fondo.dao;

import java.util.List;


import work.javiermantilla.fondo.entity.TransaccionEntity;

public interface TransaccionDAO {
	List<TransaccionEntity> findAll(String id);
	List<TransaccionEntity> findAllActive(String id);
	TransaccionEntity findById(String id);
	TransaccionEntity findByIdLoad(String idKey, String rangeKey);
	
	
}
