package com.ll.healthweb;

import com.ll.healthweb.domain.post.daily.entity.DailyCheckPost;
import com.ll.healthweb.domain.post.daily.repository.DailyCheckPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class HealthWebApplicationTests {

    @Autowired
    private DailyCheckPostRepository dailyCheckPostRepository;

    @Test
    void testJpa() {
        DailyCheckPost dailyCheckPost = new DailyCheckPost();
        dailyCheckPost.setSubject("Test Subject");
        dailyCheckPost.setContent("Test Content");
        dailyCheckPost.setCreateDate(LocalDateTime.now());
        dailyCheckPostRepository.save(dailyCheckPost);

        DailyCheckPost dailyCheckPost2 = new DailyCheckPost();
        dailyCheckPost2.setSubject("Test Subject2");
        dailyCheckPost2.setContent("Test Content2");
        dailyCheckPost2.setCreateDate(LocalDateTime.now());
        dailyCheckPostRepository.save(dailyCheckPost2);
    }

}
