package com.ll.healthweb.controller;

import com.ll.healthweb.dto.member.MemberResponseDTO;
import com.ll.healthweb.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 목록 조회
     *
     * @param model
     * @return 회원 목록 페이지
     */
    @GetMapping("/list")
    public String members(Model model) {
        List<MemberResponseDTO> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "/members/memberList";
    }

    /**
     * 회원 정보 조회
     * @param model
     * @param authentication 인증 정보
     * @return 회원 정보 페이지
     */
    @GetMapping("/info")
    public String memberInfo(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        MemberResponseDTO member = memberService.findMember(userDetails.getUsername());

        model.addAttribute("member", member);

        return "/members/info";
    }

    /**
     * 회원 정보 수정
     * @param model
     * @param authentication 인증 정보
     * @return 회원 정보 수정 페이지
     */
    @GetMapping("/update")
    public String updateForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        MemberResponseDTO member = memberService.findMember(userDetails.getUsername());

        model.addAttribute("member", member);

        return "/members/update";
    }
}