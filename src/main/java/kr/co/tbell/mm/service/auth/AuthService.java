package kr.co.tbell.mm.service.auth;

import kr.co.tbell.mm.dto.auth.ReIssue;
import kr.co.tbell.mm.entity.refreshtoken.RefreshToken;
import kr.co.tbell.mm.exception.InvalidTokenException;
import kr.co.tbell.mm.jwt.JwtManager;
import kr.co.tbell.mm.repository.refreshtoken.RefreshTokenRepository;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtManager jwtManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ReIssue reIssue(String refreshToken) {
        // Token이 만료됐는지 체크. 만료되면 ExpiredJwtException 예외가 터지고 해당 예외 공통 처리
        jwtManager.isExpired(refreshToken);

        String category = jwtManager.getCategory(refreshToken);

        validateRefreshToken(refreshToken, category);

        String username = jwtManager.getUsername(refreshToken);
        String role = jwtManager.getRole(refreshToken);

        // 새 Access, Refresh Token 생성
        String newAccessToken =
                jwtManager.createJwt(Constants.HEADER_KEY_ACCESS_TOKEN, username, role, Constants.ACCESS_EXPIRED_MS);
        String newRefreshToken =
                jwtManager.createJwt(Constants.HEADER_KEY_REFRESH_TOKEN, username, role, Constants.REFRESH_EXPIRED_MS);

        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        refreshTokenRepository.save(
            RefreshToken.valueOf(username, newRefreshToken, Constants.REFRESH_EXPIRED_MS)
        );

        return ReIssue
                .builder()
                .newAccessToken(newAccessToken)
                .newRefreshToken(newRefreshToken)
                .build();
    }

    private void validateRefreshToken(String refreshToken, String category) {
        // Token이 Refresh Token이 맞는지 체크
        if (!category.equals(Constants.HEADER_KEY_REFRESH_TOKEN)) {
            throw new InvalidTokenException("Refresh token is invalid");
        }

        // 서버에 저장된 Refresh Token인지 확인
        Optional<RefreshToken> optionalFindRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (optionalFindRefreshToken.isEmpty()) {
            throw new InvalidTokenException("Invalid refresh token");
        }
    }
}
