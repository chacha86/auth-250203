package com.example.auth.domain.member.member.service;

import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.member.member.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(String username, String password, String nickname) {

        UUID uuid = UUID.randomUUID();

        Member member = Member.builder()
                .username(username)
                .password(password)
                .password2(uuid.toString())
                .nickname(nickname)
                .build();

        return memberRepository.save(member);
    }

    public long count() {
        return memberRepository.count();
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByPassword2(String password2) {
        return memberRepository.findByPassword2(password2);
    }
}