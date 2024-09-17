package work.javiermantilla.fondo.modules.fund.services;

import java.util.List;

import work.javiermantilla.fondo.basic.utils.ETipoTransaccion;
import work.javiermantilla.fondo.modules.fund.dto.FondoDTO;
import work.javiermantilla.fondo.modules.transaction.dto.MovimientoDTO;

public interface FondosServices {
	List<FondoDTO> getFondos();
	List<FondoDTO> getFondosActivos(String id);
	List<FondoDTO> getFondosInactivos(String id);
	FondoDTO movimientoFondo(MovimientoDTO movimientoDTO,ETipoTransaccion tipo);
	
	
}
