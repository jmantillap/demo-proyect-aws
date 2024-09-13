package work.javiermantilla.fondo.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	@Override
	public TransaccionEntity getTransaccionFondoActivo(String cliente, String fondo) {		
		return this.transaccionDAO.findActiveFondo(cliente, fondo);
	}

	@Override
	public boolean saveTransacciones(TransaccionEntity updateTransaccionEntity,
			TransaccionEntity newTransaccionEntity) {

	    final String uuid = UUID.randomUUID().toString();
	    newTransaccionEntity.setId(uuid);
	    newTransaccionEntity.setFecha(new Date().toString());
	    
	    this.transaccionDAO.saveOrUpdate(updateTransaccionEntity);
	    this.transaccionDAO.saveOrUpdate(newTransaccionEntity);	    
		return true;
	}

	@Override
	public boolean saveApertura(TransaccionEntity newTransaccionEntity) {
		final String uuid = UUID.randomUUID().toString();
	    newTransaccionEntity.setId(uuid);
	    newTransaccionEntity.setFecha(new Date().toString());
	    return true;	    
	}

}
