package com.ll.healthweb.domain.share.controller;

import com.ll.healthweb.domain.comment.form.CommentForm;
import com.ll.healthweb.domain.share.Form.ShareInfoPostForm;
import com.ll.healthweb.domain.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.share.service.ShareInfoPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/info")
@RequiredArgsConstructor
@Controller
public class ShareInfoPostController {

    @Autowired
    private ShareInfoPostService shareInfoPostService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<ShareInfoPost> paging = this.shareInfoPostService.getList(page);
        model.addAttribute("paging", paging);
        return "share_list";
    }

    @GetMapping("/detail/{id}")
    public String infoDetail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        ShareInfoPost shareInfoPost = this.shareInfoPostService.getShareInfoPost(id);
        model.addAttribute("shareInfoPost", shareInfoPost);
        return "share_detail";
    }

    @GetMapping("/create")
    public String infoCreate(ShareInfoPostForm shareInfoPostForm) {
        return "share_form";
    }

    @PostMapping("/create")
    public String infoCreate(@Valid ShareInfoPostForm shareInfoPostForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "share_form";
        }
        this.shareInfoPostService.create(shareInfoPostForm.getSubject(), shareInfoPostForm.getContent());
        return "redirect:/info/list";
    }

}
