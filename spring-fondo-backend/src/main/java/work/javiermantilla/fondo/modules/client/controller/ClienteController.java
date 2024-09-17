package work.javiermantilla.fondo.modules.client.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.basic.dto.GenericResponseDTO;
import work.javiermantilla.fondo.basic.utils.FondoConstantes;
import work.javiermantilla.fondo.modules.client.services.ClienteServices;


@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
public class ClienteController {
	private final ClienteServices clienteServices;
	private GenericResponseDTO genericResponse;
	
	@GetMapping("/{id}") 
	public ResponseEntity<Object> getCliente(@PathVariable String id) {
		
		log.info("obtener cliente id: {}",id);		
		genericResponse = new GenericResponseDTO(
				this.clienteServices.getCliente(id),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
	
	@GetMapping("/{id}/{email}") 
	public ResponseEntity<Object> getClienteById(@PathVariable String id,@PathVariable String email) {
		
		log.info("obtener cliente id: {}",id);		
		genericResponse = new GenericResponseDTO(
				this.clienteServices.getClienteById(id,email),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
}
