package work.javiermantilla.fondo.security.dto;

import java.io.Serializable;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
//import com.unboundid.util.json.JSONException;
import com.unboundid.util.json.JSONObject;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContextSessionDTO implements Serializable {

	private static final long serialVersionUID = 7868287024406061790L;
	
	private String idUser;
    private String name;    
    private String email;
    private Date expirationDate;
    
    public String toString(){
        return new Gson().toJson(this);
    }
    
    @SneakyThrows
    public UserContextSessionDTO toUserContextSessionDto(String json) /*throws JSONException, ParseException*/ {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss a", Locale.ENGLISH);
        JSONObject objetoJson = new JSONObject(json);
        UserContextSessionDTO userContextSessionDTO = new UserContextSessionDTO();
        userContextSessionDTO.setIdUser(objetoJson.getFieldAsString("idUser"));
        userContextSessionDTO.setName(objetoJson.getFieldAsString("name"));
        userContextSessionDTO.setEmail(objetoJson.getFieldAsString("email"));
        userContextSessionDTO.setExpirationDate(format.parse(objetoJson.getFieldAsString("expirationDate")));
        return userContextSessionDTO;    }

}
