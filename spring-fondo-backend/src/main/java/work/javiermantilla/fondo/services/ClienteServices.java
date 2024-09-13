package work.javiermantilla.fondo.services;

import work.javiermantilla.fondo.dto.ClienteDTO;
import work.javiermantilla.fondo.entity.ClienteEntity;


public interface ClienteServices {
	ClienteDTO  getCliente(String id);
	ClienteDTO  getClienteByIdDTO(String id,String email);
	ClienteEntity  getClienteById(String id,String email);
	boolean saveCliente(ClienteEntity clienteEntity);
}
