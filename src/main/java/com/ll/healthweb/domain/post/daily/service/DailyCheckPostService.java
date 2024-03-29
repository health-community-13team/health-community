package com.ll.healthweb.domain.post.daily.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.repository.DailyCheckPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DailyCheckPostService {
    private final DailyCheckPostRepository dailyCheckPostRepository;

    public List<DailyCheckPost> getList() {
        return dailyCheckPostRepository.findAll();
    }

    public DailyCheckPost getDailyCheckPost(Integer id) {
        Optional<DailyCheckPost> dailyCheckPost = dailyCheckPostRepository.findById(id);
        if (dailyCheckPost.isPresent()) {
            return dailyCheckPost.get();
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

    public void create(String subject, String content, Member member) {
        DailyCheckPost dailyCheckPost = new DailyCheckPost();
        dailyCheckPost.setSubject(subject);
        dailyCheckPost.setContent(content);
        dailyCheckPost.setCreateDate(LocalDateTime.now());
        dailyCheckPost.setAuthor(member);
        dailyCheckPostRepository.save(dailyCheckPost);
    }

    public Page<DailyCheckPost> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return dailyCheckPostRepository.findAll(pageable);
    }

    public void modify(DailyCheckPost dailyCheckPost, String subject, String content) {
        dailyCheckPost.setSubject(subject);
        dailyCheckPost.setContent(content);
        dailyCheckPost.setModifyDate(LocalDateTime.now());
        dailyCheckPostRepository.save(dailyCheckPost);
    }

    public void delete(DailyCheckPost dailyCheckPost) {
        dailyCheckPostRepository.delete(dailyCheckPost);
    }

    public void vote(DailyCheckPost dailyCheckPost, Member member) {
        dailyCheckPost.getVoter().add(member);
        dailyCheckPostRepository.save(dailyCheckPost);
    }
}
