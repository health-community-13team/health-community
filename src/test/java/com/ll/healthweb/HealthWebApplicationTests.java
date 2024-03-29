package com.ll.healthweb;

import com.ll.healthweb.domain.post.daily.service.DailyCheckPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthWebApplicationTests {

    @Autowired
    private DailyCheckPostService dailyCheckPostService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.dailyCheckPostService.create(subject, content, null);
        }
    }

}
