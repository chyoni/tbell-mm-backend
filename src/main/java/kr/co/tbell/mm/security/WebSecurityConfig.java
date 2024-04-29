package kr.co.tbell.mm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // 한개의 아이디에 대해 최대 중복 로그인 개수 maximumSessions, maxSessionPreventsLogin 다중 로그인 개수를 초과했을 때 처리방법. true: 새로운 로그인 차단, false: 기존 세션 하나 삭제
        http.sessionManagement(sessionManagement ->
                sessionManagement.maximumSessions(1).maxSessionsPreventsLogin(false));

        http.authorizeHttpRequests(request ->
                request.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                        .anyRequest().authenticated());

        // 로그인이 되어 있지 않을때 어떤 URL에 접근하면 자동으로 아래 지정한 화면으로 리다이렉트
        /*http.formLogin(httpSecurityFormLoginConfigurer ->
                httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll());*/

        http.httpBasic(Customizer.withDefaults());

        http.logout(LogoutConfigurer::permitAll);


        return http.build();
    }
}
