package kr.co.tbell.mm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableJpaAuditing
public class MmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmApplication.class, args);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setMaxAge(6000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean =
                new FilterRegistrationBean<>(new CorsFilter(source));

        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }
}
