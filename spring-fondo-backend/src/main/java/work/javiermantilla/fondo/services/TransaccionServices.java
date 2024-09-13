package work.javiermantilla.fondo.services;

import java.util.List;

import work.javiermantilla.fondo.entity.TransaccionEntity;

public interface TransaccionServices {
	List<TransaccionEntity> getTransaccionByCliente(String cliente);
	List<TransaccionEntity> getTransaccionByClienteActivas(String cliente);	
	TransaccionEntity getTransaccionByID(String idTransaccion);
	TransaccionEntity getTransaccionFondoActivo(String cliente,String fondo);
	boolean saveTransacciones(TransaccionEntity updateTransaccionEntity,TransaccionEntity newTransaccionEntity );
	boolean saveApertura(TransaccionEntity newTransaccionEntity  );
	
	
	
}
