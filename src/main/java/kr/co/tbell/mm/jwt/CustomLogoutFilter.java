package kr.co.tbell.mm.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.entity.refreshtoken.RefreshToken;
import kr.co.tbell.mm.repository.refreshtoken.RefreshTokenRepository;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtManager jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request,
                          HttpServletResponse response,
                          FilterChain filterChain) throws IOException, ServletException {
        // Logout path 확인
        String requestURI = request.getRequestURI();
        if (!requestURI.matches(".*/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Logout Method 확인
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Cookie에 들어있는 Refresh Token 찾기
        AtomicReference<String> refresh = new AtomicReference<>();
        Arrays.stream(request.getCookies()).forEach(cookie -> {
            if (cookie.getName().equals(Constants.HEADER_KEY_REFRESH_TOKEN)) {
                refresh.set(cookie.getValue());
            }
        });

        if (refresh.get() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Refresh Token 만료 확인
        try {
            jwtManager.isExpired(refresh.get());
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Already logged out");
            return;
        }

        // Refresh Token이 맞는지 확인 (Access Token이 아닌지)
        String category = jwtManager.getCategory(refresh.get());
        if (!category.equals(Constants.HEADER_KEY_REFRESH_TOKEN)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid token");
            return;
        }

        // DB에 저장된 Refresh Token인지 확인
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refresh.get());
        if (optionalRefreshToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid token");
            return;
        }

        // 로그아웃 진행
        // DB에서 Refresh Token 삭제
        refreshTokenRepository.deleteByRefreshToken(refresh.get());

        Cookie cookie = new Cookie(Constants.HEADER_KEY_REFRESH_TOKEN, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
