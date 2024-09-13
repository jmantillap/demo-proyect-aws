package work.javiermantilla.fondo.dao;

import work.javiermantilla.fondo.entity.ClienteEntity;

public interface ClienteDAO {
	ClienteEntity getCliente(String id);
	ClienteEntity getClienteById(String  hashKey, String rangeKey);
}
