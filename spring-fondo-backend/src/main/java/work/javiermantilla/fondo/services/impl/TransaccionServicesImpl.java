package work.javiermantilla.fondo.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import work.javiermantilla.fondo.dao.TransaccionDAO;
import work.javiermantilla.fondo.entity.TransaccionEntity;
import work.javiermantilla.fondo.services.TransaccionServices;


@Service
@RequiredArgsConstructor
public class TransaccionServicesImpl implements TransaccionServices {

	private final TransaccionDAO transaccionDAO;
	
	@Override
	public List<TransaccionEntity> getTransaccionByCliente(String cliente) {
		return this.transaccionDAO.findAll(cliente);
	}

	@Override
	public List<TransaccionEntity> getTransaccionByClienteActivas(String cliente) {
		return this.transaccionDAO.findAllActive(cliente);
	}

	@Override
	public TransaccionEntity getTransaccionByID(String idTransaccion) {
		return this.transaccionDAO.findById(idTransaccion);
	}

}
