package kr.co.tbell.mm.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.dto.administrator.CustomAdministratorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;
    public static final int REFRESH_TOKEN_COOKIE_MAX_AGE = 24 * 60 * 60; // 24시간(초단위)
    private static final long ACCESS_EXPIRED_MS = 1800000L; // 30분
    private static final long REFRESH_EXPIRED_MS = 86400000L; // 24시간

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("[attemptAuthentication]: Username : {}, Password: {}", username, password);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        log.info("[successfulAuthentication]: Authentication Success");
        String username = authentication.getName();
        String role = authentication
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();

        String accessToken = jwtManager.createJwt("access", username, role, ACCESS_EXPIRED_MS);
        String refreshToken = jwtManager.createJwt("refresh", username, role, REFRESH_EXPIRED_MS);

        response.setHeader("Access-Token", accessToken);
        response.addCookie(createCookie("Refresh-Token", refreshToken));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        log.info("[unsuccessfulAuthentication]: Authentication Failed");

        String errorMessage = String.format("error: Failed login with this username: %s", obtainUsername(request));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(errorMessage);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(REFRESH_TOKEN_COOKIE_MAX_AGE);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
