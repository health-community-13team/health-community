package com.ll.healthweb.domain.post.daily.entity;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import com.ll.healthweb.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class DailyCheckPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "dailyCheckPost", cascade = CascadeType.REMOVE)
    private List<DailyCheckComment> dailyCheckCommentList;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<Member> voter;
}
