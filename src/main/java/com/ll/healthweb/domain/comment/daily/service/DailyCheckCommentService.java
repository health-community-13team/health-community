package com.ll.healthweb.domain.comment.daily.service;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import com.ll.healthweb.domain.comment.daily.repository.DailyCheckCommentRepository;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DailyCheckCommentService {
    private final DailyCheckCommentRepository dailyCheckCommentRepository;

    public void createDailyCheckComment(DailyCheckPost dailyCheckPost, String content) {
        DailyCheckComment dailyCheckComment = new DailyCheckComment();
        dailyCheckComment.setContent(content);
        dailyCheckComment.setCreateDate(LocalDateTime.now());
        dailyCheckComment.setDailyCheckPost(dailyCheckPost);
        dailyCheckCommentRepository.save(dailyCheckComment);
    }
}
