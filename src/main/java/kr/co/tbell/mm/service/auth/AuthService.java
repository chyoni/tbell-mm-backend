package kr.co.tbell.mm.service.auth;

import kr.co.tbell.mm.dto.auth.ReIssue;
import kr.co.tbell.mm.exception.InvalidTokenException;
import kr.co.tbell.mm.jwt.JwtManager;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtManager jwtManager;

    public ReIssue reIssue(String refreshToken) {
        // Token이 만료됐는지 체크 만료되면, ExpiredJwtException 예외가 터지고 해당 예외 공통 처리
        jwtManager.isExpired(refreshToken);

        String category = jwtManager.getCategory(refreshToken);

        // Token이 Refresh Token이 맞는지 체크
        if (!category.equals(Constants.HEADER_KEY_REFRESH_TOKEN)) {
            throw new InvalidTokenException("Refresh token is invalid");
        }

        String username = jwtManager.getUsername(refreshToken);
        String role = jwtManager.getRole(refreshToken);

        // 새 Access, Refresh Token 생성
        String newAccessToken =
                jwtManager.createJwt(Constants.HEADER_KEY_ACCESS_TOKEN, username, role, Constants.ACCESS_EXPIRED_MS);
        String newRefreshToken =
                jwtManager.createJwt(Constants.HEADER_KEY_REFRESH_TOKEN, username, role, Constants.REFRESH_EXPIRED_MS);

        return ReIssue
                .builder()
                .newAccessToken(newAccessToken)
                .newRefreshToken(newRefreshToken)
                .build();
    }
}
