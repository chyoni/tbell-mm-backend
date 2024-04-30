package kr.co.tbell.mm.config;

import kr.co.tbell.mm.entity.administrator.Role;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // JWT 방식을 사용하기 때문에 CSRF 공격에 대한 위험성 X
        http.csrf(AbstractHttpConfigurer::disable);

        // JWT 방식을 사용하기 때문에 FormLogin, Basic 방식을 Disable
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 한개의 아이디에 대해 최대 중복 로그인 개수 maximumSessions
        // maxSessionPreventsLogin 다중 로그인 개수를 초과했을 때 처리방법. true: 새로운 로그인 차단, false: 기존 세션 하나 삭제
        // JWT 방식을 사용하기 때문에 SessionCreationPolicy STATELESS
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request ->
                request.requestMatchers(
                        new AntPathRequestMatcher("/api/v1/admin/signup", "POST"),
                        new AntPathRequestMatcher("/login", "POST")).permitAll()
                        .anyRequest().hasRole(Role.ROLE_ADMIN.getDescription()));

        http.addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration)),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
