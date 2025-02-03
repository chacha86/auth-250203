package com.example.auth.domain.member.member.controller;

import com.example.auth.domain.member.member.dto.MemberDto;
import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.member.member.service.MemberService;
import com.example.auth.global.dto.RsData;
import com.example.auth.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    record JoinReqBody(@NotBlank @Length(min = 3) String username,
                       @NotBlank @Length(min = 3) String password,
                       @NotBlank @Length(min = 3) String nickname) {
    }

    @PostMapping("/join")
    public RsData<MemberDto> join(@RequestBody @Valid JoinReqBody body) {


        memberService.findByUsername(body.username())
                .ifPresent(member -> {
                    throw new ServiceException("400-1", "중복된 아이디입니다.");
                });


        Member member = memberService.join(body.username(), body.password(), body.nickname());

        return new RsData<>(
                "201-1",
                "회원 가입이 완료되었습니다.",
                new MemberDto(member)
        );
    }


    record LoginReqBody(@NotBlank @Length(min = 3) String username,
                        @NotBlank @Length(min = 3) String password) {
    }

    record LoginResBody(MemberDto memberDto, String apiKey) {
    }

    @PostMapping("/login")
    public RsData<LoginResBody> login(@RequestBody @Valid LoginReqBody body) {

        Member actor = memberService.findByUsername(body.username())
                .orElseThrow(() -> new ServiceException("401-2", "아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!actor.getPassword().equals(body.password())) {
            throw new ServiceException("401-2", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return new RsData<>(
                "200-1",
                "%s님 환영합니다.".formatted(actor.getNickname()),
                new LoginResBody(
                        new MemberDto(actor),
                        actor.getApiKey()
                )
        );
    }

    @GetMapping("/me")
    public RsData<MemberDto> me() {
        Member actor = getAuthenticatedActor();

        return new RsData<>(
                "200-1",
                "내 정보 조회가 완료되었습니다.",
                new MemberDto(actor)
        );
    }

    private Member getAuthenticatedActor() {

        String authorizationValue = request.getHeader("Authorization");
        String apiKey = authorizationValue.substring("Bearer ".length());
        Optional<Member> opActor = memberService.findByApiKey(apiKey);

        if(opActor.isEmpty()) {
            throw new ServiceException("401-1", "잘못된 비밀번호 입니다.");
        }

        return opActor.get();

    }
}
