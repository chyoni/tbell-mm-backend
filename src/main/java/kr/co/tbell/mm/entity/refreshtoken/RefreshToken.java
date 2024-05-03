package kr.co.tbell.mm.entity.refreshtoken;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.tbell.mm.entity.BaseEntity;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String refreshToken;
    private String expiration;

    public static RefreshToken valueOf(String username, String refreshToken, Long expiredMs) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiredMs);

        return RefreshToken
                .builder()
                .username(username)
                .refreshToken(refreshToken)
                .expiration(expirationDate.toString())
                .build();
    }
}
