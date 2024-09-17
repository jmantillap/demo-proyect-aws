package work.javiermantilla.fondo.modules.fund.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FondoDTO implements Serializable {
    
	private static final long serialVersionUID = -3352038996451178857L;
	private String id;		
    private String codigo;	
	private String categoria;	
	private Float monto;	
	private String nombre;
}
