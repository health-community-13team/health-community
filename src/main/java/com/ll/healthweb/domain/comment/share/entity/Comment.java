package com.ll.healthweb.domain.comment.share.entity;

import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.share.entity.ShareInfoPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShareInfoPost shareInfoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToMany
    Set<Member> like;

}
