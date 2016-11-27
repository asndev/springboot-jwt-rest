package ninja.asn.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by hknd on 27.11.16.
 */
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";
    private final String secret;

    public JwtFilter(String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String authHeader = req.getHeader(AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Token ")) {
            throw new ServletException("Not a valid authentication authHeader");
        }

        String compactJws = authHeader.substring(6);

        try {
            Claims token = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(compactJws)
                    .getBody();

            // TODO(asn): extract user for this token and append it to the request.

            servletRequest.setAttribute("token", token);
        } catch (SignatureException ex) {
            throw new ServletException("Invalid Token");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
