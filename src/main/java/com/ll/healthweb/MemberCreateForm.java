package com.ll.healthweb;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class MemberCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수 입력 항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String passwordRepeated;

    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email
    private String email;
}
