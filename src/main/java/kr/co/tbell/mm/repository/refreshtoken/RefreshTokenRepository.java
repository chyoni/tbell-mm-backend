package kr.co.tbell.mm.repository.refreshtoken;

import kr.co.tbell.mm.entity.refreshtoken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
