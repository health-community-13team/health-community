package com.ll.healthweb.domain.comment.share.controller;

import com.ll.healthweb.domain.comment.share.entity.Comment;
import com.ll.healthweb.domain.comment.share.form.CommentForm;
import com.ll.healthweb.domain.comment.share.service.CommentService;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.member.service.MemberService;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.post.share.service.ShareInfoPostService;
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

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final ShareInfoPostService shareInfoPostService;
    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        Member member = this.memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("shareInfoPost", shareInfoPost);
            return "share_detail";
        }
        Comment comment = this.commentService.create(shareInfoPost, commentForm.getContent(), member);
        this.commentService.create(shareInfoPost, commentForm.getContent(), member);
        return String.format("redirect:/info/detail/%s#comment_%s", comment.getShareInfoPost().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment commnet = this.commentService.getComment(id);
        if (!commnet.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(commnet.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Comment comment = this.commentService.getComment(id);
        if (!comment.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/info/detail/%s#comment_%s", comment.getShareInfoPost().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/info/detail/%s", comment.getShareInfoPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String commentLike(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        Member member = this.memberService.getMember(principal.getName());
        this.commentService.like(comment, member);
        return String.format("redirect:/info/detail/%s#comment_%s", comment.getShareInfoPost().getId(), comment.getId());
    }
}
