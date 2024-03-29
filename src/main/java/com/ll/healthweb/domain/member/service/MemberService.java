package com.ll.healthweb.domain.member.service;

import com.ll.healthweb.DataNotFoundException;
import com.ll.healthweb.domain.member.entity.Member;
import com.ll.healthweb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String password, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);

        return member;
    }

    public Member getUser(String username) {
        Optional<Member> siteUser = this.memberRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
