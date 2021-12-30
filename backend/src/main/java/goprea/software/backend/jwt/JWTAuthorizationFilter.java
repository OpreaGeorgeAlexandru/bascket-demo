package goprea.software.backend.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LogManager.getLogger(JWTAuthorizationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        super(authenticationManager);
        jwtTokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;

        try {
            authentication = jwtTokenProvider.getAuthentication(request);
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Token has expired!");
        } catch (SignatureException ex) {
            LOGGER.error("JWT token is no longer valid!");
        }

        if (authentication != null && jwtTokenProvider.validateToken(request)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
