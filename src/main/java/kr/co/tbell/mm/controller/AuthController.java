package kr.co.tbell.mm.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.jwt.JwtManager;
import kr.co.tbell.mm.utils.Constants;
import kr.co.tbell.mm.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtManager jwtManager;

    @PostMapping("/reissue")
    public ResponseEntity<Response<?>> reissue(
            @CookieValue(name = Constants.HEADER_KEY_REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, "Refresh token not found", null));
        }

        // Token이 만료됐는지 체크
        try {
            jwtManager.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, "Refresh token expired", null));
        }

        String category = jwtManager.getCategory(refreshToken);
        // Token이 Refresh Token이 맞는지 체크
        if (!category.equals(Constants.HEADER_KEY_REFRESH_TOKEN)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, "Refresh token is invalid", null));
        }

        String username = jwtManager.getUsername(refreshToken);
        String role = jwtManager.getRole(refreshToken);

        // 새 Access, Refresh Token 생성
        String newAccessToken =
                jwtManager.createJwt(Constants.HEADER_KEY_ACCESS_TOKEN, username, role, Constants.ACCESS_EXPIRED_MS);
        String newRefreshToken =
                jwtManager.createJwt(Constants.HEADER_KEY_REFRESH_TOKEN, username, role, Constants.REFRESH_EXPIRED_MS);

        // 새 Access, Refresh Token을 Response의 Headers에 추가
        response.setHeader(Constants.HEADER_KEY_ACCESS_TOKEN, newAccessToken);
        response.addCookie(Utils.createCookie(Constants.HEADER_KEY_REFRESH_TOKEN, newRefreshToken));

        return ResponseEntity.ok(
                new Response<>(true,
                        "Refresh token success",
                        null));
    }
}
