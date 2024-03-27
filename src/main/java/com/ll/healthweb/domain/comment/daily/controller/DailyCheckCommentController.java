package com.ll.healthweb.domain.comment.daily.controller;

import com.ll.healthweb.domain.comment.daily.service.DailyCheckCommentService;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.service.DailyCheckPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/daily")
@RequiredArgsConstructor
public class DailyCheckCommentController {
    private final DailyCheckPostService dailyCheckPostService;
    private final DailyCheckCommentService dailyCheckCommentService;

    @PostMapping("/create/{id}")
    public String createDailyCheckPostComment(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content") String content) {
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);
        dailyCheckCommentService.createDailyCheckComment(dailyCheckPost, content);

        return String.format("redirect:/daily/detail/%s", id);
    }
}
