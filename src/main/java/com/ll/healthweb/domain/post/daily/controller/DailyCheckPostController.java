package com.ll.healthweb.domain.post.daily.controller;

import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.service.DailyCheckPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/daily")
public class DailyCheckPostController {

    private final DailyCheckPostService dailyCheckPostService;

    @GetMapping("/list")
    public String list(Model model) {
        List<DailyCheckPost> dailyCheckPostList = dailyCheckPostService.getList();
        model.addAttribute("dailyCheckPostList", dailyCheckPostList);

        return "daily_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);

        model.addAttribute("dailyCheckPost", dailyCheckPost);

        return "daily_detail";
    }
}
