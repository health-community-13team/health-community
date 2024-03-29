package com.ll.healthweb.domain.comment.daily.service;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import com.ll.healthweb.domain.comment.daily.repository.DailyCheckCommentRepository;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyCheckCommentService {
    private final DailyCheckCommentRepository dailyCheckCommentRepository;

    public void createDailyCheckComment(DailyCheckPost dailyCheckPost, String content, Member author) {
        DailyCheckComment dailyCheckComment = new DailyCheckComment();
        dailyCheckComment.setContent(content);
        dailyCheckComment.setCreateDate(LocalDateTime.now());
        dailyCheckComment.setDailyCheckPost(dailyCheckPost);
        dailyCheckComment.setAuthor(author);
        dailyCheckCommentRepository.save(dailyCheckComment);
    }

    public DailyCheckComment getDailyCheckComment(Integer id) {
        Optional<DailyCheckComment> dailyCheckComment = dailyCheckCommentRepository.findById(id);
        if (dailyCheckComment.isPresent()) {
            return dailyCheckComment.get();
        } else {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }
    }

    public void modify(DailyCheckComment answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.dailyCheckCommentRepository.save(answer);
    }

    public void delete(DailyCheckComment answer) {
        dailyCheckCommentRepository.delete(answer);
    }

    public void vote(DailyCheckComment answer, Member siteUser) {
        answer.getVoter().add(siteUser);
        dailyCheckCommentRepository.save(answer);
    }
}
