package work.javiermantilla.fondo.services;

import work.javiermantilla.fondo.dto.ClienteDTO;

public interface ClienteServices {
	ClienteDTO  getCliente(String id);
	ClienteDTO  getClienteById(String id,String email);
}
