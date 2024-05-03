package kr.co.tbell.mm.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.tbell.mm.dto.auth.ReIssue;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.exception.InvalidTokenException;
import kr.co.tbell.mm.service.auth.AuthService;
import kr.co.tbell.mm.utils.Constants;
import kr.co.tbell.mm.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<Response<?>> reissue(
            @CookieValue(name = Constants.HEADER_KEY_REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null) {
            throw new InvalidTokenException("Refresh token not found");
        }

        ReIssue reIssue = authService.reIssue(refreshToken);

        // 새 Access, Refresh Token을 Response의 Headers에 추가
        response.setHeader(Constants.HEADER_KEY_ACCESS_TOKEN, reIssue.getNewAccessToken());
        response.addCookie(Utils.createCookie(Constants.HEADER_KEY_REFRESH_TOKEN, reIssue.getNewRefreshToken()));

        return ResponseEntity.ok(new Response<>(true, null, null));
    }
}
