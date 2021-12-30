package goprea.software.backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.token.prefix}")
    private String jwtTokenPrefix;

    @Value("${app.jwt.header.string}")
    private String jwtHeaderString;

    @Value("${app.jwt.expiration-in-ms}")
    private Long jwtExpirationInMs;

    public String generateToken(Authentication auth) {
        final String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("roles", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    Authentication getAuthentication(HttpServletRequest request) {
        final String token = resolveToken(request);

        if (token == null) {
            return null;
        }

        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        final String username = claims.getSubject();
        final List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
    }

    boolean validateToken(HttpServletRequest request) {
        final String token = resolveToken(request);

        if (token == null) {
            return false;
        }

        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return (new Date()).before(claims.getExpiration());
    }

    private String resolveToken(HttpServletRequest req) {
        final String bearerToken = req.getHeader(jwtHeaderString);

        if (bearerToken == null || !bearerToken.startsWith(jwtTokenPrefix)) {
            return null;
        }

        return bearerToken.substring(jwtTokenPrefix.length() + 1);
    }

}
