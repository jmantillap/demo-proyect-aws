package work.javiermantilla.fondo.modules.fund.dao;

import java.util.List;

import work.javiermantilla.fondo.modules.fund.entity.FondoEntity;

public interface FondoDAO {

	List<FondoEntity> findAll();
	FondoEntity findByID(String id);
}
