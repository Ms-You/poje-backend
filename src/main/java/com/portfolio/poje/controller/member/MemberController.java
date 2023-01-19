package com.portfolio.poje.controller.member;

import com.portfolio.poje.common.BasicResponse;
import com.portfolio.poje.common.exception.ErrorCode;
import com.portfolio.poje.config.SecurityUtil;
import com.portfolio.poje.config.jwt.TokenDto;
import com.portfolio.poje.controller.member.memberDto.*;
import com.portfolio.poje.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 회원가입
     * @param memberJoinRequestDto
     * @return
     */
    @Tag(name = "Members")
    @Operation(summary = "회원가입", description = "memberJoinRequestDto 필드들로 회원가입한다.", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @PostMapping("/join")
    public ResponseEntity<BasicResponse> join(@RequestBody @Validated MemberJoinRequestDto memberJoinRequestDto){
        memberService.join(memberJoinRequestDto);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.CREATED.value(), "회원가입 성공"));
    }


    /**
     * 로그인 아이디 중복 확인
     * @param loginId
     * @return
     */
    @GetMapping("/loginId/{loginId}/check")
    public ResponseEntity<BasicResponse> loginIdDuplicate(@PathVariable(value = "loginId") String loginId){
        BasicResponse basicResponse;
        if (memberService.loginIdCheck(loginId)){
            basicResponse = new BasicResponse(ErrorCode.BAD_REQUEST.getStatus().value(), "이미 존재하는 아이디입니다.");
        } else{
            basicResponse = new BasicResponse(HttpStatus.OK.value(), "사용할 수 있는 아이디입니다.");
        }

        return ResponseEntity.ok(basicResponse);
    }


    /**
     * 사용자 로그인
     * @param memberLoginRequestDto
     * @param response
     * @return
     */
    @Tag(name = "Members")
    @Operation(summary = "로그인", description = "memberLoginRequestDto 필드들로 로그인한다.")
    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody @Validated MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response){
        TokenDto tokenDto = memberService.login(memberLoginRequestDto);

        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "로그인 성공"));
    }


    /**
     * 사용자 정보 반환
     * @return memberInfoResponseDto
     */
    @GetMapping("/member/info")
    public ResponseEntity<BasicResponse> getMemberInfo(){
        MemberInfoResponseDto memberInfoResponseDto = memberService.getMemberInfo(SecurityUtil.getCurrentMemberId());

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "회원 정보 조회", memberInfoResponseDto));
    }


    /**
     * 사용자 정보 수정
     * @param memberUpdateRequestDto
     * @return memberInfoResponseDto
     */
    @PutMapping("/member")
    public ResponseEntity<BasicResponse> updateMemberInfo(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto){
        MemberInfoResponseDto memberInfoResponseDto = memberService.updateMember(memberUpdateRequestDto);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.ACCEPTED.value(), "회원 정보가 수정되었습니다.", memberInfoResponseDto));
    }


    /**
     * 로그아웃
     * @return
     */
    @PostMapping("/member/logout")
    public ResponseEntity<BasicResponse> logout(){
        // 로그아웃 시 Refresh Token 삭제
        memberService.deleteRefreshToken(SecurityUtil.getCurrentMemberId());
        
        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "로그아웃 되었습니다."));
    }


    /**
     * access token 만료 시 재발행
     * @param tokenRequestDto
     * @param response
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<BasicResponse> reissue(@RequestBody TokenRequestDto tokenRequestDto, HttpServletResponse response){
        TokenDto tokenDto = memberService.reissue(tokenRequestDto);

        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "재발급 되었습니다."));
    }


    public ResponseCookie setRefreshToken(String refreshToken){
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60 * 24)
                .sameSite("None")
                .path("/")
                .build();

        return cookie;
    }

}
