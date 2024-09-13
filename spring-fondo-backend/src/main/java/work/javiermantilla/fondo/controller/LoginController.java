package work.javiermantilla.fondo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dto.AutenticationDTO;
import work.javiermantilla.fondo.dto.GenericResponseDTO;
import work.javiermantilla.fondo.services.LoginServices;
import work.javiermantilla.fondo.util.FondoConstantes;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
public class LoginController {
	private GenericResponseDTO genericResponse;
	private final LoginServices loginServices;

	@PostMapping("/login")
	public ResponseEntity<GenericResponseDTO> autenticacion(@Valid @RequestBody AutenticationDTO login,
			HttpServletResponse response) {

		String token = this.loginServices.getTokenExisteCliente(login.getId());
		response = this.loginServices.createCookieSession(token, response);		
		String dataCookie =this.loginServices.getDataCookie(response.getHeader("Set-Cookie"));
		genericResponse = new GenericResponseDTO(
				dataCookie ,
				true, 
				FondoConstantes.RESPONSE_LOGIN,
				HttpStatus.OK, 
				FondoConstantes.TITTLE_FIND);
		log.info("Generación de cookie: {}",dataCookie);
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
}
