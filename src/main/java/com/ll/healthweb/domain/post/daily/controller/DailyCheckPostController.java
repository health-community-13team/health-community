package com.ll.healthweb.domain.post.daily.controller;

import com.ll.healthweb.domain.comment.daily.form.DailyCheckCommentForm;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.member.service.MemberService;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.form.DailyCheckPostForm;
import com.ll.healthweb.domain.post.daily.service.DailyCheckPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/daily")
public class DailyCheckPostController {

    private final DailyCheckPostService dailyCheckPostService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<DailyCheckPost> paging = this.dailyCheckPostService.getList(page);
        model.addAttribute("paging", paging);

        return "daily_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, DailyCheckCommentForm dailyCheckCommentForm) {
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);

        model.addAttribute("dailyCheckPost", dailyCheckPost);

        return "daily_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(DailyCheckPostForm dailyCheckPostForm) {
        return "daily_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid DailyCheckPostForm dailyCheckPostForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "daily_form";
        }
        Member member = memberService.getMember(principal.getName());

        dailyCheckPostService.create(dailyCheckPostForm.getSubject(), dailyCheckPostForm.getContent(), member);
        return "redirect:/daily/list"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(DailyCheckPostForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        DailyCheckPost question = dailyCheckPostService.getDailyCheckPost(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "daily_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid DailyCheckPostForm dailyCheckPostForm, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "daily_form";
        }
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);
        if (!dailyCheckPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        dailyCheckPostService.modify(dailyCheckPost, dailyCheckPostForm.getSubject(), dailyCheckPostForm.getContent());
        return String.format("redirect:/daily/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable("id") Integer id) {
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);
        if (!dailyCheckPost.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        dailyCheckPostService.delete(dailyCheckPost);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        DailyCheckPost question = this.dailyCheckPostService.getDailyCheckPost(id);
        Member siteUser = this.memberService.getMember(principal.getName());
        this.dailyCheckPostService.vote(question, siteUser);
        return String.format("redirect:/daily/detail/%s", id);
    }

}