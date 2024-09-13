package work.javiermantilla.fondo.services;

import work.javiermantilla.fondo.dto.FondoDTO;

public interface EmailServices {
	boolean sendEmail(FondoDTO fondo);
}
