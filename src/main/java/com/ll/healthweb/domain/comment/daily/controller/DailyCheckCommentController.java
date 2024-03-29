package com.ll.healthweb.domain.comment.daily.controller;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import com.ll.healthweb.domain.comment.daily.form.DailyCheckCommentForm;
import com.ll.healthweb.domain.comment.daily.service.DailyCheckCommentService;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.member.service.MemberService;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.service.DailyCheckPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/daily/comment")
@RequiredArgsConstructor
public class DailyCheckCommentController {
    private final DailyCheckPostService dailyCheckPostService;
    private final DailyCheckCommentService dailyCheckCommentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createDailyCheckPostComment(Model model, @PathVariable("id") Integer id, @Valid DailyCheckCommentForm dailyCheckCommentForm, BindingResult bindingResult, Principal principal) {
        DailyCheckPost dailyCheckPost = dailyCheckPostService.getDailyCheckPost(id);
        Member member = memberService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("dailyCheckPost", dailyCheckPost);
            return "daily_detail";
        }
        this.dailyCheckCommentService.createDailyCheckComment(dailyCheckPost, dailyCheckCommentForm.getContent(), member);

        return String.format("redirect:/daily/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(DailyCheckCommentForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        DailyCheckComment answer = this.dailyCheckCommentService.getDailyCheckComment(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "daily_comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyDailyCheckPostComment(@Valid DailyCheckCommentForm dailyCheckCommentForm, BindingResult bindingResult,
                                              @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "daily_comment_form";
        }
        DailyCheckComment dailyCheckComment = this.dailyCheckCommentService.getDailyCheckComment(id);
        if (!dailyCheckComment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.dailyCheckCommentService.modify(dailyCheckComment, dailyCheckCommentForm.getContent());
        return String.format("redirect:/daily/detail/%s", dailyCheckComment.getDailyCheckPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteDailyCheckPostComment(@PathVariable("id") Integer id, Principal principal) {
        DailyCheckComment dailyCheckComment = this.dailyCheckCommentService.getDailyCheckComment(id);
        if (!dailyCheckComment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.dailyCheckCommentService.delete(dailyCheckComment);
        return String.format("redirect:/daily/detail/%s", dailyCheckComment.getDailyCheckPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        DailyCheckComment answer = this.dailyCheckCommentService.getDailyCheckComment(id);
        Member siteUser = this.memberService.getUser(principal.getName());
        this.dailyCheckCommentService.vote(answer, siteUser);
        return String.format("redirect:/daily/detail/%s", answer.getDailyCheckPost().getId());
    }
}
