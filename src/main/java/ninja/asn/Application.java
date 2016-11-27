package ninja.asn;

import ninja.asn.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


/**
 * Created by hknd on 12.10.16.
 */
@SpringBootApplication
public class Application {

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

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
