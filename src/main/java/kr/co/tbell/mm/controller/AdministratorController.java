package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.administrator.CustomAdministratorDetails;
import kr.co.tbell.mm.dto.administrator.ReqCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ResCreateAdministrator;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.entity.administrator.Administrator;
import kr.co.tbell.mm.entity.administrator.Role;
import kr.co.tbell.mm.service.administrator.AdministratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PostMapping("/signup")
    public ResponseEntity<Response<ResCreateAdministrator>> signup(
            @RequestBody @Valid ReqCreateAdministrator reqCreateAdministrator) {
        ResCreateAdministrator administrator = administratorService.createAdministrator(reqCreateAdministrator);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, administrator));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Response<CustomAdministratorDetails>> administratorDetails() {
        // 스프링 시큐리티 컨텍스트에서 현재 로그인 유저 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Administrator administrator = Administrator
                .builder()
                .username(authentication.getName())
                .role(Role.getRole(authentication.getAuthorities().iterator().next().getAuthority()))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, new CustomAdministratorDetails(administrator)));
    }
}
