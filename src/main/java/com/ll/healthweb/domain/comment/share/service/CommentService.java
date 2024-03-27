package com.ll.healthweb.domain.comment.share.service;

import com.ll.healthweb.domain.comment.share.entity.Comment;
import com.ll.healthweb.domain.comment.share.repository.CommentRepository;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private  final CommentRepository commentRepository;

    public void create(ShareInfoPost shareInfoPost, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setShareInfoPost(shareInfoPost);
        this.commentRepository.save(comment);
    }

}
