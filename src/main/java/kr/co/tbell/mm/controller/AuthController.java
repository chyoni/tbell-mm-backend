package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.administrator.ReqCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ReqLogin;
import kr.co.tbell.mm.dto.administrator.ResCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ResLogin;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.service.administrator.AdministratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdministratorService administratorService;

    @PostMapping("/signup")
    public ResponseEntity<Response<ResCreateAdministrator>> signup(
            @RequestBody @Valid ReqCreateAdministrator reqCreateAdministrator) {
        ResCreateAdministrator administrator;

        try {
            administrator = administratorService.createAdministrator(reqCreateAdministrator);
        } catch (InstanceAlreadyExistsException e) {
            // TODO: 체크 예외로 잡은 에러를 겨우 e.getMessage()로 응답해주는거면 아예 언체크 예외로 공통 처리할 수 있지 않을까?
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, administrator));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<ResLogin>> login(@RequestBody @Valid ReqLogin reqLogin) {
        ResLogin session = administratorService.login(reqLogin);

        if (session == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response<>(false, "Username or password is incorrect", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, session));
    }
}
