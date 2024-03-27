package com.ll.healthweb.domain.comment.daily.repository;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyCheckCommentRepository extends JpaRepository<DailyCheckComment, Integer> {
}
