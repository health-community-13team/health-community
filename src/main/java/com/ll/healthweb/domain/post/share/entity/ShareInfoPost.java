package com.ll.healthweb.domain.post.share.entity;

import com.ll.healthweb.domain.comment.share.entity.Comment;
import com.ll.healthweb.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareInfoPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private member memberId;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
//    private Integer view;
//    private Category categoryId;
//    private Integer likes;

    @OneToMany(mappedBy = "shareInfoPost", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToMany
    Set<Member> like;

}
