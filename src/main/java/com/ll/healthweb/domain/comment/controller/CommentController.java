package com.ll.healthweb.domain.comment.controller;

import com.ll.healthweb.domain.comment.form.CommentForm;
import com.ll.healthweb.domain.comment.service.CommentService;
import com.ll.healthweb.domain.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.share.service.ShareInfoPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final ShareInfoPostService shareInfoPostService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("shareInfoPost", shareInfoPost);
            return "share_detail";
        }
        this.commentService.create(shareInfoPost, commentForm.getContent());
        return String.format("redirect:/info/detail/%s", id);
    }
}
