package ml.geekdjenika.apiinfrabaana.jwt;

import io.jsonwebtoken.*;
import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Service.userDetails.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ToString
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${infractions.app.jwtSecret}")
    private String jwtSecret;

    @Value("${infractions.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("La signature du JWT est invalide: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Le token du JWT est invalid: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Le token du JWT est expiré: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Le token du JWT n'est supporté: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Le claims du JWT est vide: {}", e.getMessage());
        }

        return false;
    }

}
