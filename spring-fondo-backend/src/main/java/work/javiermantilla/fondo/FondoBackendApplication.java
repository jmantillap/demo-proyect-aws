package work.javiermantilla.fondo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class FondoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FondoBackendApplication.class, args);
	}

}
