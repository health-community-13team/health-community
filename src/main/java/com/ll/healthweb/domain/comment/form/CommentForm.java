package com.ll.healthweb.domain.comment.form;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수입력 항목입니다.")
    private String content;
}
