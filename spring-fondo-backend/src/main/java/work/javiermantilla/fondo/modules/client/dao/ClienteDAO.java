package work.javiermantilla.fondo.modules.client.dao;

import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;

public interface ClienteDAO {
	ClienteEntity getCliente(String id);
	ClienteEntity getClienteById(String  hashKey, String rangeKey);
	boolean saveOrUpdate(ClienteEntity clienteEntity);
}
