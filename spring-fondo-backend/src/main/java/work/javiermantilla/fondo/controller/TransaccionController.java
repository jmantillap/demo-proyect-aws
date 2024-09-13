package work.javiermantilla.fondo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dto.GenericResponseDTO;
import work.javiermantilla.fondo.services.TransaccionServices;
import work.javiermantilla.fondo.util.FondoConstantes;

@RestController
@RequestMapping("/api/transaccion")
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
public class TransaccionController {
	private GenericResponseDTO genericResponse;
	private final TransaccionServices transaccionServices;
	
	@GetMapping("/{cliente}") 
	public ResponseEntity<Object> getCliente(@PathVariable String cliente) {
		
		log.info("transacciones del cliente id: {}",cliente);		
		genericResponse = new GenericResponseDTO(
				this.transaccionServices.getTransaccionByCliente(cliente),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
}
