package work.javiermantilla.fondo.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.ClienteDAO;
import work.javiermantilla.fondo.dto.ClienteDTO;
import work.javiermantilla.fondo.entity.ClienteEntity;
import work.javiermantilla.fondo.services.ClienteServices;
import work.javiermantilla.fondo.util.GenericMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClienteServicesImpl implements ClienteServices {
	
	
	private final ClienteDAO clienteDynamoDAO;
	
	
	@Override
	public ClienteDTO getCliente(String id) {
		
		ClienteEntity cliente = this.clienteDynamoDAO.getCliente(id);
		if(cliente==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no Existe");
		}		
		return GenericMapper.map(cliente, ClienteDTO.class);
	}


	@Override
	public ClienteDTO getClienteById(String id,String email) {
		ClienteEntity cliente = this.clienteDynamoDAO.getClienteById(id,email);
		if(cliente==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no Existe");
		}
		return GenericMapper.map(cliente, ClienteDTO.class);
	}

}
