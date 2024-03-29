package com.ll.healthweb.domain.post.share.repository;

import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareInfoPostRepository extends JpaRepository<ShareInfoPost, Integer> {
    ShareInfoPost findBySubject(String subject);

    ShareInfoPost findBySubjectAndContent(String subject, String content);

    List<ShareInfoPost> findBySubjectLike(String subject);

    Page<ShareInfoPost> findAll(Pageable pageable);

    Page<ShareInfoPost> findAll(Specification<ShareInfoPost> spec, Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from ShareInfoPost q "
            + "left outer join Member u1 on q.member=u1 "
            + "left outer join Comment a on a.shareInfoPost=q "
            + "left outer join Member u2 on a.member=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<ShareInfoPost> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
