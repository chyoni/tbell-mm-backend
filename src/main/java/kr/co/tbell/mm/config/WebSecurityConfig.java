package kr.co.tbell.mm.config;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.tbell.mm.entity.administrator.Role;
import kr.co.tbell.mm.jwt.JwtFilter;
import kr.co.tbell.mm.jwt.JwtManager;
import kr.co.tbell.mm.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtManager jwtManager;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* CORS
        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList("*"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                return configuration;
            }
        }));
        */

        // JWT 방식을 사용하기 때문에 CSRF 공격에 대한 위험성 X
        http.csrf(AbstractHttpConfigurer::disable);

        // JWT 방식을 사용하기 때문에 FormLogin, Basic 방식을 Disable
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 한개의 아이디에 대해 최대 중복 로그인 개수 maximumSessions
        // maxSessionPreventsLogin 다중 로그인 개수를 초과했을 때 처리방법. true: 새로운 로그인 차단, false: 기존 세션 하나 삭제
        // JWT 방식을 사용하기 때문에 SessionCreationPolicy STATELESS
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // AntPathRequestMatcher 객체를 사용한 이유는 개인적으로 가시성이 좀 더 좋고 어떤 Http Method인지 바로 알 수 있어서 사용
        // 굳이 안 사용해도 된다.
        http.authorizeHttpRequests(request ->
                request.requestMatchers(
                        new AntPathRequestMatcher("/api/v1/admin/signup", "POST"),
                        new AntPathRequestMatcher("/login", "POST")
                        ).permitAll()
                        .anyRequest().hasRole("ADMIN"));

        // LoginFilter 앞에 JwtFilter 등록
        http.addFilterBefore(new JwtFilter(jwtManager), LoginFilter.class);

        // addFilterAt은 정확히 그 필터(UsernamePasswordAuthenticationFilter)를 내가 만든 LoginFilter로 대체하겠다는 메서드.
        // addFilterBefore, addFilterAfter 이 것들은 말 그대로 그 전 또는 그 후에 붙이겠다는 의미
        http.addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtManager),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
