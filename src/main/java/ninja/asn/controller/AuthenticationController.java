package ninja.asn.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ninja.asn.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.*;

/**
 * Created by hknd on 27.11.16.
 */
@RestController
@RequestMapping("/")
public class AuthenticationController {

    private static final String ISSUER = "v1-api";

    @Value("${ninja.asn.secret}")
    private String secret;

    private Map<String, String> users = new HashMap<>();

    public AuthenticationController() {
        users.put("admin", "foobar");
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody AuthenticationRequest auth) throws ServletException {
        String password = users.get(auth.getUsername());

        if (auth.getPassword() == null || !auth.getPassword().equals(password)) {
            throw new ServletException("Invalid user");
        }

        String token = Jwts.builder()
                .setSubject(auth.getUsername())
                .setIssuedAt(new Date())
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return new LoginResponse(token);
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public LoginResponse signup(@RequestBody AuthenticationRequest auth) throws ServletException {
        users.put(auth.getUsername(), auth.getPassword());

        return login(auth);
    }

    private static class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}
