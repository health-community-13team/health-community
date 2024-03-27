package com.ll.healthweb.domain.post.daily.entity;

import com.ll.healthweb.domain.comment.daily.entity.DailyCheckComment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
}
