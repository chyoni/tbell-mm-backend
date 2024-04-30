package kr.co.tbell.mm.entity.administrator;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public static Role getRole(String description) {
        return Arrays.stream(Role.values())
                .filter(role -> role.description.equals(description))
                .findFirst()
                .orElse(null);
    }
}
