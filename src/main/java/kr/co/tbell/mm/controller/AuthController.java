package kr.co.tbell.mm.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.tbell.mm.dto.auth.ReIssue;
import kr.co.tbell.mm.dto.auth.ReqReIssue;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.exception.InvalidTokenException;
import kr.co.tbell.mm.service.auth.AuthService;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<Response<ReIssue>> reissue(@RequestBody ReqReIssue reqReIssue) {
        log.info("[reissue]: refreshToken={}", reqReIssue.getRefreshToken());

        if (reqReIssue.getRefreshToken() == null) {
            throw new InvalidTokenException("Refresh token not found");
        }

        ReIssue reIssue = authService.reIssue(reqReIssue.getRefreshToken());

        return ResponseEntity.ok(new Response<>(true, null, reIssue));
    }
}
