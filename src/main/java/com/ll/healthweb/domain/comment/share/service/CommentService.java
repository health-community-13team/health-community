package com.ll.healthweb.domain.comment.share.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.comment.share.entity.Comment;
import com.ll.healthweb.domain.comment.share.repository.CommentRepository;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private  final CommentRepository commentRepository;

    public Comment create(ShareInfoPost shareInfoPost, String content, Member member) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setShareInfoPost(shareInfoPost);
        comment.setMember(member);
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void like(Comment comment, Member member) {
        comment.getLike().add(member);
        this.commentRepository.save(comment);
    }

}
