package kr.co.tbell.mm.entity.administrator;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.BaseEntity;
import lombok.*;

@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean isExpired;
    @Column(nullable = false)
    private boolean isLocked;
    @Column(nullable = false)
    private boolean isCredentialsExpired;

    @Builder.Default
    @Column(nullable = false)
    private boolean isEnabled = true;

    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
