package kr.co.tbell.mm.dto.department;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqCreateDepartment {
    @NotNull(message = "'name' must be required.")
    private String name;
}
