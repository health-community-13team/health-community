package com.ll.healthweb.domain.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @GetMapping("/list")
    public String list() {
        return "question_list";
    }
}
