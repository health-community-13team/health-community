package com.ll.healthweb.domain.post.share.repository;

import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareInfoPostRepository extends JpaRepository<ShareInfoPost, Integer> {
    ShareInfoPost findBySubject(String subject);

    ShareInfoPost findBySubjectAndContent(String subject, String content);

    List<ShareInfoPost> findBySubjectLike(String subject);

    Page<ShareInfoPost> findAll(Pageable pageable);
}
