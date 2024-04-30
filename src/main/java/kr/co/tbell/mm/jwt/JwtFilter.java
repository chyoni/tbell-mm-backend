package kr.co.tbell.mm.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.dto.administrator.CustomAdministratorDetails;
import kr.co.tbell.mm.entity.administrator.Administrator;
import kr.co.tbell.mm.entity.administrator.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("[doFilterInternal]: JWT token does not exist.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        if (jwtManager.isExpired(token)) {
            log.error("[doFilterInternal]: JWT token expired.");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtManager.getUsername(token);
        String roleStringValue = jwtManager.getRole(token);

        // 현재 로그인 한 사용자 정보를 기반으로 Administrator 객체 생성
        Administrator administrator = Administrator
                .builder()
                .username(username)
                .role(Role.getRole(roleStringValue))
                .build();

        // UserDetails 회원 정보 객체에 현재 로그인 한 사용자 담기
        CustomAdministratorDetails administratorDetails = new CustomAdministratorDetails(administrator);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                administratorDetails,
                null,
                administratorDetails.getAuthorities());

        // 스프링 시큐리티 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
