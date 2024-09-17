package work.javiermantilla.fondo.modules.transaction.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import work.javiermantilla.fondo.validators.OneOf;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoDTO implements Serializable {
	
	private static final long serialVersionUID = -2419084875358940436L;
	@NotNull
	@NotBlank
	@Size(min = 1,max = 50)
	private String fondo;
//	@OneOf(allowedValues = {"A","C"}, message = "El valor no es válido. Solo se permite: A,C (Apertura, Cancelación) ")
//	private String tipo;	
	
}
