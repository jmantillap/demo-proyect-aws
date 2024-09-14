package work.javiermantilla.fondo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins = "*")
@Log4j2
public class HealthController {	
	
	@GetMapping("/health") 
	public ResponseEntity<Object> getFondos() {
		
		Map<String, Object> response = new HashMap<>();
		response.put("OK", "OK");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

