package com.ll.healthweb.domain.post.share.controller;

import com.ll.healthweb.domain.comment.share.form.CommentForm;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.member.service.MemberService;
import com.ll.healthweb.domain.post.share.Form.ShareInfoPostForm;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.post.share.service.ShareInfoPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/info")
@RequiredArgsConstructor
@Controller
public class ShareInfoPostController {

    @Autowired
    private ShareInfoPostService shareInfoPostService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<ShareInfoPost> paging = this.shareInfoPostService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "share_list";
    }

    @GetMapping("/detail/{id}")
    public String infoDetail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        model.addAttribute("shareInfoPost", shareInfoPost);
        return "share_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String infoCreate(ShareInfoPostForm shareInfoPostForm) {
        return "share_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String infoCreate(@Valid ShareInfoPostForm shareInfoPostForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "share_form";
        }
        Member member = this.memberService.getMember(principal.getName());
        this.shareInfoPostService.create(shareInfoPostForm.getSubject(), shareInfoPostForm.getContent(), member);
        return "redirect:/info/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String infoModify(ShareInfoPostForm shareInfoPostForm, @PathVariable("id") Integer id, Principal principal) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        if(!shareInfoPost.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        shareInfoPostForm.setSubject(shareInfoPost.getSubject());
        shareInfoPostForm.setContent(shareInfoPost.getContent());
        return "share_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String infoModify(@Valid ShareInfoPostForm shareInfoPostForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "share_form";
        }
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        if (!shareInfoPost.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.shareInfoPostService.modify(shareInfoPost, shareInfoPostForm.getSubject(), shareInfoPostForm.getContent());
        return String.format("redirect:/info/detail/%s", id);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String infoDelete(Principal principal, @PathVariable("id") Integer id) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        if (!shareInfoPost.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.shareInfoPostService.delete(shareInfoPost);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String infoLike(Principal principal, @PathVariable("id") Integer id) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        Member member = this.memberService.getMember(principal.getName());
        this.shareInfoPostService.like(shareInfoPost, member);
        return String.format("redirect:/info/detail/%s", id);
    }
}
