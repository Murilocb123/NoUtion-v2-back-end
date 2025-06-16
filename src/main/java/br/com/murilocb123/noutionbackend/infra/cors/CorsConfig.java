package br.com.murilocb123.noutionbackend.infra.cors;

import br.com.murilocb123.noutionbackend.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Value("${allowed.origin1}")
    private String alowedOrigin1;

    @Value("${allowed.origin2}")
    private String alowedOrigin2;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Adding CORS mappings");
        log.info("Allowed Origin 1: {}", alowedOrigin1);
        log.info("Allowed Origin 2: {}", alowedOrigin2);
        registry.addMapping("/**")
                .allowedOrigins(alowedOrigin1, alowedOrigin2)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH");
    }
}