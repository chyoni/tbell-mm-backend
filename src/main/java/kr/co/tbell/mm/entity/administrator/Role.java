package kr.co.tbell.mm.entity.administrator;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ADMIN");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
