package work.javiermantilla.fondo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FondoDTO implements Serializable {
    
	private static final long serialVersionUID = -3352038996451178857L;
	private String id;		
    private String codigo;	
	private String categoria;	
	private float monto;	
	private String nombre;
}
