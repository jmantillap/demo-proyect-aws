package work.javiermantilla.fondo.modules.client.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.basic.utils.GenericMapper;
import work.javiermantilla.fondo.modules.client.dao.ClienteDAO;
import work.javiermantilla.fondo.modules.client.dto.ClienteDTO;
import work.javiermantilla.fondo.modules.client.entity.ClienteEntity;
import work.javiermantilla.fondo.modules.client.services.ClienteServices;

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
	public ClienteDTO getClienteByIdDTO(String id,String email) {
		ClienteEntity cliente = this.getClienteById(id, email);
		return GenericMapper.map(cliente, ClienteDTO.class);
	}


	@Override
	public ClienteEntity getClienteById(String id, String email) {
		ClienteEntity cliente = this.clienteDynamoDAO.getClienteById(id,email);
		if(cliente==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no Existe");
		}
		return cliente;
	}


	@Override
	public boolean saveCliente(ClienteEntity clienteEntity) {		
		return this.clienteDynamoDAO.saveOrUpdate(clienteEntity);
	}

}
