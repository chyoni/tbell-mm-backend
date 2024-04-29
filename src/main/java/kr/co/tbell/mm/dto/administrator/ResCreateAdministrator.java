package kr.co.tbell.mm.dto.administrator;

import kr.co.tbell.mm.entity.administrator.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResCreateAdministrator {
    private Long id;
    private String username;
    private Role role;
}
