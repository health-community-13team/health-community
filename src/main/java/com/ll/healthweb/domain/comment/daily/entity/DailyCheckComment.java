package com.ll.healthweb.domain.comment.daily.entity;

import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class DailyCheckComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private DailyCheckPost dailyCheckPost;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<Member> voter;
}
