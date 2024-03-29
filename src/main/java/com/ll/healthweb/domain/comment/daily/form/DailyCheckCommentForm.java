package com.ll.healthweb.domain.comment.daily.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyCheckCommentForm {
    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}
