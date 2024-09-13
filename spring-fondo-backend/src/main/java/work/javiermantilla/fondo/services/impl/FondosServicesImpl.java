package work.javiermantilla.fondo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.fondo.dao.FondoDAO;
import work.javiermantilla.fondo.dto.FondoDTO;
import work.javiermantilla.fondo.dto.MovimientoAperturaDTO;
import work.javiermantilla.fondo.dto.MovimientoDTO;
import work.javiermantilla.fondo.entity.ClienteEntity;
import work.javiermantilla.fondo.entity.FondoEntity;
import work.javiermantilla.fondo.entity.TransaccionEntity;
import work.javiermantilla.fondo.security.ContextSession;
import work.javiermantilla.fondo.security.dto.UserContextSessionDTO;
import work.javiermantilla.fondo.services.ClienteServices;
import work.javiermantilla.fondo.services.EmailServices;
import work.javiermantilla.fondo.services.FondosServices;
import work.javiermantilla.fondo.services.TransaccionServices;
import work.javiermantilla.fondo.util.ETipoTransaccion;
import work.javiermantilla.fondo.util.GenericMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class FondosServicesImpl implements FondosServices {

	private final FondoDAO fondoDAO;
	private final TransaccionServices transaccionServices;
	private final ClienteServices clienteServices;
	private final ContextSession contextSession;
	private final EmailServices emailServices;

	@Override
	public List<FondoDTO> getFondos() {
		List<FondoEntity> list = this.fondoDAO.findAll();
		return GenericMapper.mapList(list, FondoDTO.class);
	}

	@Override
	public List<FondoDTO> getFondosActivos(String cliente) {

		List<TransaccionEntity> listTransacciones = this.transaccionServices.getTransaccionByClienteActivas(cliente);

		List<String> fondosActivosCliente = listTransacciones.stream().map(TransaccionEntity::getFondo).toList();

		List<FondoEntity> listFondos = this.fondoDAO.findAll();

		List<FondoEntity> listActivos = listFondos.stream().filter(f -> fondosActivosCliente.contains(f.getId()))
				.toList();
		
		listActivos.forEach(f->{
			Optional<Float> monto = listTransacciones.stream()
					.filter(t->t.getFondo().equals(f.getId()))
					.map(TransaccionEntity::getValor)
					.findAny();
			f.setMonto(monto.get().toString());
		});
		
		

		return GenericMapper.mapList(listActivos, FondoDTO.class);
	}

	@Override
	public List<FondoDTO> getFondosInactivos(String cliente) {
		List<FondoDTO> fondosActivosCliente = this.getFondosActivos(cliente);
		List<FondoEntity> listFondos = this.fondoDAO.findAll();

		List<String> codigosActivosCliente = fondosActivosCliente.stream().map(FondoDTO::getId).toList();

		List<FondoEntity> listInactivos = listFondos.stream().filter(f -> !codigosActivosCliente.contains(f.getId()))
				.toList();

		return GenericMapper.mapList(listInactivos, FondoDTO.class);
	}

	
	private boolean cancelarFondo(MovimientoDTO movimientoDTO) {
		UserContextSessionDTO usuario = this.contextSession.getContextSessionThread();
		ClienteEntity cliente = this.clienteServices.getClienteById(usuario.getIdUser(), usuario.getEmail());
		TransaccionEntity transaccionFondo = this.transaccionServices
				.getTransaccionFondoActivo(usuario.getIdUser(), movimientoDTO.getFondo());
		if (transaccionFondo == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no tiene suscrito este fondo");
		}
		
		transaccionFondo.setActiva(false);		
		Float nuevoSaldo= Float.valueOf(cliente.getSaldo()) + transaccionFondo.getValor();
		cliente.setSaldo(nuevoSaldo.toString());
		
		TransaccionEntity newTransaccion = TransaccionEntity.builder()
				.activa(false)
				.cliente(usuario.getIdUser())
				.fondo(transaccionFondo.getFondo())
				.valor(transaccionFondo.getValor())
				.tipo(ETipoTransaccion.CANCELACION.getCode())
				.build();
		
		this.transaccionServices.saveTransacciones(transaccionFondo, newTransaccion);		
		this.clienteServices.saveCliente(cliente);		
		return true;
	}

	@Override
	public FondoDTO movimientoFondo(MovimientoDTO movimientoDTO, ETipoTransaccion tipo) {
		
		if(ETipoTransaccion.CANCELACION.equals(tipo)) {
			this.cancelarFondo(movimientoDTO);
		}else if(ETipoTransaccion.APERTURA.equals(tipo)) {
			this.aperturaFondo((MovimientoAperturaDTO)movimientoDTO);			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de transaccion no definido");
		}
		List<FondoDTO> list= this.getFondos();
		Optional<FondoDTO> oFondo=list.stream().filter(f-> f.getId().equals(movimientoDTO.getFondo()))
				.findAny();
		if(oFondo.isPresent()) {
			oFondo.get().setMonto(null);
		}
		
		return oFondo.orElse(new FondoDTO()); 
	}
	
	private boolean aperturaFondo(MovimientoAperturaDTO movimientoDTO) {
		UserContextSessionDTO usuario = this.contextSession.getContextSessionThread();
		ClienteEntity cliente = this.clienteServices.getClienteById(usuario.getIdUser(), usuario.getEmail());
		TransaccionEntity transaccionFondo = this.transaccionServices
				.getTransaccionFondoActivo(usuario.getIdUser(), movimientoDTO.getFondo());
		if (transaccionFondo != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente tiene activo este fondo");
		}
		List<FondoDTO> list= this.getFondos();
		Optional<FondoDTO> oFondo=list.stream().filter(f-> f.getId().equals(movimientoDTO.getFondo()))
				.findAny();
		if(oFondo.isEmpty()) {
			return false;
		}
		Float valorMinimoFondo= oFondo.get().getMonto();
		if( movimientoDTO.getMonto() < valorMinimoFondo ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"No puede suscribirse al fondo porque el valor dado es menor al minimo requerido");
		}
		if(movimientoDTO.getMonto() > Float.valueOf(cliente.getSaldo())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"El cliente no tiene suficiente saldo");
		}
		
		Float nuevoSaldo = Float.valueOf(cliente.getSaldo()) - movimientoDTO.getMonto();
		cliente.setSaldo(nuevoSaldo.toString());
		
		TransaccionEntity newTransaccion = TransaccionEntity.builder()
				.activa(true)
				.cliente(usuario.getIdUser())
				.fondo(movimientoDTO.getFondo())
				.valor(movimientoDTO.getMonto())
				.tipo(ETipoTransaccion.APERTURA.getCode())
				.build();
		
		this.transaccionServices.saveApertura(newTransaccion);		
		this.clienteServices.saveCliente(cliente);
		this.emailServices.sendEmail(oFondo.get());				
		return true;
	}

}
