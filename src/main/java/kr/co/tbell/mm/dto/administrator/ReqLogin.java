package kr.co.tbell.mm.dto.administrator;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqLogin {
    @NotNull(message = "'username' must be required.")
    private String username;
    @NotNull(message = "'password' must be required.")
    private String password;
}
