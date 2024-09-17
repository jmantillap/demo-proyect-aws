package work.javiermantilla.fondo.modules.transaction.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDTO implements Serializable {

	
	private static final long serialVersionUID = 9216577534754363426L;
	private String id;
	private String cliente;	
	private String fondo;	
	private float valor;	
	private String fecha;	
	private boolean activa;
	private String tipo;
}
