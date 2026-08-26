package spring_security_jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Transforma la clave secreta en un objeto SecretKey para firmar el token
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Genera un token JWT para un usuario dado
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    // Extrae todos los claims contenidos en el token.
    private Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    // Extrae un claim específico del token utilizando un resolver proporcionado.
    public <T> T extractClaim(
            String token,
            Function<Claims,T> resolver
    ){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Extrae el nombre de usuario (subject) del token.
    public String extractUsername(String token){
        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    // Extrae la fecha de expiración del token.
    public Date extractExpiration(String token){
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    // Valida si el token ya ha expirado comparando la fecha de expiración con la fecha actual.
    public boolean isTokenExpired(String token){
        return extractExpiration(token)
                .before(new Date());

    }

    //Verifica dos cosas
    //Comprueba que el usuario del token coincide con el usuario recuperado por UserDetailsService
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
