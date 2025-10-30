package kakaotechbootcamp.communityservice.repository;

import kakaotechbootcamp.communityservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);
    Optional<RefreshToken> findByUserId(Long userId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteByUserId(Long userId);
}
