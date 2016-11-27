package ninja.asn.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by hknd on 27.11.16.
 */
@Component
public class JwtFilterBean {

    @Value("${ninja.asn.secret}")
    private String secret;

    @Bean
    public FilterRegistrationBean jwtFiler() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        // Registers the jwt filter to intercept each request which tries to access the api route.
        registrationBean.setFilter(new JwtFilter(secret));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
