package work.javiermantilla.fondo.modules.email.services;

import work.javiermantilla.fondo.modules.fund.dto.FondoDTO;

public interface EmailServices {
	boolean sendEmail(FondoDTO fondo);
}
