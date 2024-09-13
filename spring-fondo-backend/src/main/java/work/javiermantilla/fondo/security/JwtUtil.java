package work.javiermantilla.fondo.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import work.javiermantilla.fondo.security.dto.UserContextSessionDTO;
import work.javiermantilla.fondo.util.FondoConstantes;

@Component
public class JwtUtil {

	@SuppressWarnings("deprecation")
	public boolean validateJWT(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(FondoConstantes.APIKEY))
					.parseClaimsJws(token).getBody();

			Date now = new Date();
			return !now.after(claims.getExpiration());
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public String createJWT(String issuer, String name,String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(FondoConstantes.TOKEN_IDUSER, issuer);
		claims.put(FondoConstantes.TOKEN_NAME, name);
		claims.put(FondoConstantes.TOKEN_EMAIL, email);
		long timeLifeJwt = System.currentTimeMillis() + FondoConstantes.TIME_LIFE_JWT;
		Date dateExpiration = new Date(timeLifeJwt);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(issuer)
				.setSubject(name)
				.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(dateExpiration)                
                .signWith(SignatureAlgorithm.HS256, FondoConstantes.APIKEY).compact();
		
		
	}

	@SuppressWarnings("deprecation")
	public UserContextSessionDTO extraerContextoJWT(String token) {
		UserContextSessionDTO user = new UserContextSessionDTO();
		SignatureAlgorithm algoritmoFirma = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FondoConstantes.APIKEY);
		Key key = new SecretKeySpec(apiKeySecretBytes, algoritmoFirma.getJcaName());
		var parser = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();		
		user.setIdUser(parser.get(FondoConstantes.TOKEN_IDUSER).toString());
		user.setName(parser.get(FondoConstantes.TOKEN_NAME).toString());
		user.setEmail(parser.get(FondoConstantes.TOKEN_EMAIL).toString());
		user.setExpirationDate(parser.getExpiration());
		return user;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(FondoConstantes.APIKEY).parseClaimsJws(token).getBody();
    }
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
