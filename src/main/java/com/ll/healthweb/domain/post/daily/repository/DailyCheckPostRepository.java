package com.ll.healthweb.domain.post.daily.repository;

import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyCheckPostRepository extends JpaRepository<DailyCheckPost, Integer> {
    DailyCheckPost findBySubject(String subject);
    DailyCheckPost findBySubjectAndContent(String subject, String content);
    List<DailyCheckPost> findBySubjectLike (String subject);
    Page<DailyCheckPost> findAll(Pageable pageable);
}
