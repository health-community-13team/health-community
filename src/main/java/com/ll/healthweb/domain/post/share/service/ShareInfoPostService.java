package com.ll.healthweb.domain.post.share.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.comment.share.entity.Comment;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.post.share.repository.ShareInfoPostRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShareInfoPostService {

    private final ShareInfoPostRepository shareInfoPostRepository;

    private Specification<ShareInfoPost> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<ShareInfoPost> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<ShareInfoPost, Member> u1 = q.join("author", JoinType.LEFT);
                Join<ShareInfoPost, Comment> a = q.join("answerList", JoinType.LEFT);
                Join<Comment, Member> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public Page<ShareInfoPost> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.shareInfoPostRepository.findAllByKeyword(kw, pageable);
    }

    public ShareInfoPost getShareInfoPost(Integer id) {
        Optional<ShareInfoPost> shareInfoPost = this.shareInfoPostRepository.findById(id);
        if(shareInfoPost.isPresent()) {
            return shareInfoPost.get();
        }
        else {
            throw new DataNotFoundException("shareInfoPost not found");
        }
    }

    public void create(String subject, String content, Member member) {
        ShareInfoPost shareInfoPost = ShareInfoPost.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .member(member)
                .build();
        this.shareInfoPostRepository.save(shareInfoPost);
    }

    public void modify(ShareInfoPost shareInfoPost, String subject, String content) {
        shareInfoPost.setSubject(subject);
        shareInfoPost.setContent(content);
        shareInfoPost.setModifyDate(LocalDateTime.now());
        this.shareInfoPostRepository.save(shareInfoPost);
    }
    public void delete(ShareInfoPost shareInfoPost) {
        this.shareInfoPostRepository.delete(shareInfoPost);
    }

    public void like(ShareInfoPost shareInfoPost, Member member) {
        shareInfoPost.getLike().add(member);
        this.shareInfoPostRepository.save(shareInfoPost);
    }
}
