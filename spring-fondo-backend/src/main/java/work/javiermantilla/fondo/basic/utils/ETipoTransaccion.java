package work.javiermantilla.fondo.basic.utils;

import java.util.HashMap;
import java.util.Map;

public enum ETipoTransaccion {
	APERTURA("A","-","Apertura"),
	CANCELACION("c","+","Cancelación"),;
	
	private String code;
	private String signo;
	private String descripcion;
	private static final Map<String, ETipoTransaccion> MAP = new HashMap<>();
	

	private ETipoTransaccion(String code, String signo,String descripcion) {
		this.code = code;
		this.descripcion = descripcion;
		this.signo=signo;
	}

	public static ETipoTransaccion fromCode(String code){
        return MAP.get(code);
    }
	
	
	public String getCode() {
		return code;
	}
	public String getSigno() {
		return signo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	static{
        for(ETipoTransaccion n : values()){
            MAP.put(n.getCode(), n);
        }
    }
}
