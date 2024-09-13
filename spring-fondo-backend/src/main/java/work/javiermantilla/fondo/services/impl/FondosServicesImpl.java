package work.javiermantilla.fondo.services.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.FondoDAO;
import work.javiermantilla.fondo.dto.FondoDTO;
import work.javiermantilla.fondo.entity.FondoEntity;
import work.javiermantilla.fondo.entity.TransaccionEntity;
import work.javiermantilla.fondo.services.FondosServices;
import work.javiermantilla.fondo.services.TransaccionServices;
import work.javiermantilla.fondo.util.GenericMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class FondosServicesImpl implements FondosServices {
	
	private final FondoDAO fondoDAO;
	private final TransaccionServices transaccionServices;
	
	@Override
	public List<FondoDTO> getFondos() {
		List<FondoEntity> list= this.fondoDAO.findAll();		
		return GenericMapper.mapList(list, FondoDTO.class);
	}

	@Override
	public List<FondoDTO> getFondosActivos(String id) {
		
		List<TransaccionEntity> listTransacciones = this.transaccionServices.getTransaccionByClienteActivas(id);
		
		List<String> fondosActivosCliente = listTransacciones
									.stream()
									.map(TransaccionEntity::getFondo)
									.toList();
		
		List<FondoEntity> listFondos= this.fondoDAO.findAll();	
		
		List<FondoEntity> listActivos = listFondos
				.stream()
				.filter(f-> fondosActivosCliente.contains(f.getId()))
				.toList();
						
				
		return GenericMapper.mapList(listActivos, FondoDTO.class);
	}

	@Override
	public List<FondoDTO> getFondosInactivos(String id) {		
		List<FondoDTO> fondosActivosCliente = this.getFondosActivos(id);		
		List<FondoEntity> listFondos= this.fondoDAO.findAll();
		
		List<String> codigosActivosCliente = fondosActivosCliente
				.stream()
				.map(FondoDTO::getId)
				.toList();
		
		List<FondoEntity> listInactivos = listFondos
				.stream()
				.filter(f-> !codigosActivosCliente.contains(f.getId()))
				.toList();		
		
		return GenericMapper.mapList(listInactivos, FondoDTO.class);
	}

}
