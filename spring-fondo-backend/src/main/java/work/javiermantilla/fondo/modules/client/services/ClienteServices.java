package work.javiermantilla.fondo.modules.client.services;

import work.javiermantilla.fondo.modules.client.dto.ClienteDTO;
import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;


public interface ClienteServices {
	ClienteDTO  getCliente(String id);
	ClienteDTO  getClienteByIdDTO(String id,String email);
	ClienteEntity  getClienteById(String id,String email);
	boolean saveCliente(ClienteEntity clienteEntity);
}
