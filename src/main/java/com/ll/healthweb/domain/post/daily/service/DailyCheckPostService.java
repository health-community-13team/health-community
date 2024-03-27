package com.ll.healthweb.domain.post.daily.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.repository.DailyCheckPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
