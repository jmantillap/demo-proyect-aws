package work.javiermantilla.fondo.modules.client.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 6221513684384323821L;
	private String id;
	private String email;	
	private String nombre;	
	private float saldo;
}
