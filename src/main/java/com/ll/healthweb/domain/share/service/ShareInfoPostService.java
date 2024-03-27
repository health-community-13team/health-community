package com.ll.healthweb.domain.share.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.share.entity.ShareInfoPost;
import com.ll.healthweb.domain.share.repository.ShareInfoPostRepository;
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
public class ShareInfoPostService {

    private final ShareInfoPostRepository shareInfoPostRepository;

    public Page<ShareInfoPost> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.shareInfoPostRepository.findAll(pageable);
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

    public void create(String subject, String content) {
        ShareInfoPost shareInfoPost = ShareInfoPost.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
        this.shareInfoPostRepository.save(shareInfoPost);
    }
}
