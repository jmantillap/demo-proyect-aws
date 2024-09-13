package work.javiermantilla.fondo.dao;

import java.util.List;


import work.javiermantilla.fondo.entity.FondoEntity;

public interface FondoDAO {

	List<FondoEntity> findAll();
	FondoEntity findByID(String id);
}
