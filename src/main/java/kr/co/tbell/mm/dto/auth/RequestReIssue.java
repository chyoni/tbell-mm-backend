package kr.co.tbell.mm.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RequestReIssue {
    private String refreshToken;
}
