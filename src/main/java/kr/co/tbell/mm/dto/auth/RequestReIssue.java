package kr.co.tbell.mm.dto.auth;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestReIssue {
    private String refreshToken;
}
