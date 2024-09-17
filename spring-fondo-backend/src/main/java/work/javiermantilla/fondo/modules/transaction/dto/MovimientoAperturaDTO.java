package work.javiermantilla.fondo.modules.transaction.dto;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovimientoAperturaDTO extends MovimientoDTO implements Serializable {

	private static final long serialVersionUID = -2419084875358940436L;
	@NotNull
	@Positive
	private float monto;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(monto);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimientoAperturaDTO other = (MovimientoAperturaDTO) obj;
		return Float.floatToIntBits(monto) == Float.floatToIntBits(other.monto);
	}

	public MovimientoAperturaDTO(String fondo, @NotNull @Positive float monto) {
		super(fondo);
		this.monto = monto;
	}
	
	
	
	
}
