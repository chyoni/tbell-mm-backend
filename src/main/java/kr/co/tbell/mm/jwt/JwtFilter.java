package kr.co.tbell.mm.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.dto.administrator.CustomAdministratorDetails;
import kr.co.tbell.mm.entity.administrator.Administrator;
import kr.co.tbell.mm.entity.administrator.Role;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 사용자가 요청을 할 때 보내는 JWT Token에 대해 인증과 검사를 하는 클래스
 * */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(Constants.HEADER_KEY_ACCESS_TOKEN);

        // Access Token이 없는 경우
        if (accessToken == null) {
            // Token이 필요없는 요청일 수도 있으니까 다음 필터로 넘겨야 한다.
            filterChain.doFilter(request, response);
            return;
        }

        // Token이 만료되었는지 체크
        try {
            jwtManager.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            response.getWriter().write("access token expired");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            // 프론트 측에서는 이 응답일 때 자신이 가지고 있는 Refresh Token을 이용해 다시 Access Token을 발급 받는 로직을 수행하면 된다.
            return;
        }

        String category = jwtManager.getCategory(accessToken);
        // Token이 Access Token이 맞는지 체크
        if (!category.equals(Constants.HEADER_KEY_ACCESS_TOKEN)) {
            response.getWriter().write("Invalid access token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String username = jwtManager.getUsername(accessToken);
        String roleAsString = jwtManager.getRole(accessToken);

        Administrator administrator = Administrator
                .builder()
                .username(username)
                .role(Role.getRole(roleAsString))
                .build();

        // CustomAdministratorDetails 객체를 토큰으로부터 햔제 로그인 한 유저 정보를 받아 생성
        CustomAdministratorDetails administratorDetails = new CustomAdministratorDetails(administrator);

        // UsernamePasswordAuthenticationToken 객체를 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                administratorDetails,
                null,
                administratorDetails.getAuthorities());

        // Spring Security Context에 현재 로그인 한 유저 정보 일시적 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
