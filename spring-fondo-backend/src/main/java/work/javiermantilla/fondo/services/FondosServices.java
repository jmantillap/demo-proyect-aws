package work.javiermantilla.fondo.services;

import java.util.List;

import work.javiermantilla.fondo.dto.FondoDTO;
import work.javiermantilla.fondo.dto.MovimientoDTO;

public interface FondosServices {
	List<FondoDTO> getFondos();
	List<FondoDTO> getFondosActivos(String id);
	List<FondoDTO> getFondosInactivos(String id);	
	FondoDTO cancelarFondo(MovimientoDTO movimientoDTO);
	
}
