package kr.co.tbell.mm.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.entity.refreshtoken.RefreshToken;
import kr.co.tbell.mm.repository.refreshtoken.RefreshTokenRepository;
import kr.co.tbell.mm.utils.Constants;
import kr.co.tbell.mm.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("[attemptAuthentication]: Attempt login, Username : {}", username);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    @Transactional
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {
        log.info("[successfulAuthentication]: Authentication Success");

        String username = authentication.getName();
        String role = authentication
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();

        String accessToken = 
                jwtManager.createJwt(Constants.HEADER_KEY_ACCESS_TOKEN, username, role, Constants.ACCESS_EXPIRED_MS);
        String refreshToken = 
                jwtManager.createJwt(Constants.HEADER_KEY_REFRESH_TOKEN, username, role, Constants.REFRESH_EXPIRED_MS);

        refreshTokenRepository.save(
                RefreshToken.valueOf(username, refreshToken, Constants.REFRESH_EXPIRED_MS)
        );

        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write("Access-Token=" + accessToken + ";Refresh-Token=" + refreshToken);
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
}
