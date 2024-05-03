package kr.co.tbell.mm.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReIssue {
    private String newAccessToken;
    private String newRefreshToken;
}
