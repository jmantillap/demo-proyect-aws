package work.javiermantilla.fondo.modules.fund.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.basic.dto.GenericResponseDTO;
import work.javiermantilla.fondo.basic.utils.ETipoTransaccion;
import work.javiermantilla.fondo.basic.utils.FondoConstantes;
import work.javiermantilla.fondo.modules.fund.services.FondosServices;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoAperturaDTO;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoDTO;

@RestController
@RequestMapping("/api/fondo")
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
public class FondoController {

	private final FondosServices fondosServices;
	private GenericResponseDTO genericResponse;
	
	@GetMapping 
	public ResponseEntity<Object> getFondos() {
		
		log.info("Lista de todos los fondos");		
		genericResponse = new GenericResponseDTO(
				this.fondosServices.getFondos(),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
	
	@GetMapping("/{id}/inscritos") 
	public ResponseEntity<Object> getFondosActivosCliente(@PathVariable String id) {
		
		log.info("Lista de fondos inscritos del cliente {}",id);		
		genericResponse = new GenericResponseDTO(
				this.fondosServices.getFondosActivos(id),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
	
	@GetMapping("/{id}/no-inscritos") 
	public ResponseEntity<Object> getFondosNoInscritosCliente(@PathVariable String id) {
		
		log.info("Lista de fondos no inscritos del cliente : {}", id);		
		genericResponse = new GenericResponseDTO(
				this.fondosServices.getFondosInactivos(id),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
	
	@PostMapping("/apertura") 
	public ResponseEntity<Object> setSuscribir(@Valid @RequestBody MovimientoAperturaDTO movimientoAperturaDTO) {
		
		log.info("Suscripcion : {}", movimientoAperturaDTO);		
		genericResponse = new GenericResponseDTO(
				this.fondosServices.movimientoFondo(movimientoAperturaDTO,ETipoTransaccion.APERTURA),
				true, 
				FondoConstantes.RESPONSE_FIND,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
	
	
	
	@PostMapping("/cancelar") 
	public ResponseEntity<Object> setCancelar(@Valid @RequestBody MovimientoDTO movimientoDTO) {
		
		log.info("Fondo a cancelar : {}", movimientoDTO);		
		genericResponse = new GenericResponseDTO(
				this.fondosServices.movimientoFondo(movimientoDTO,ETipoTransaccion.CANCELACION),
				true, 
				FondoConstantes.RESPONSE_CANCELAR,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_CANCELAR);
				
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);		
	}
}
