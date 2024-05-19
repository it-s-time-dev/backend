package Itstime.planear.member.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.member.dto.CheckMemberNameRequest;
import Itstime.planear.member.dto.CheckMemberNameResponse;
import Itstime.planear.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/check")
    public ApiResponse<CheckMemberNameResponse> checkMemberName(
            @RequestBody CheckMemberNameRequest request
    ) {
        return memberService.checkMemberName(request);
    }

    @PostMapping("/user")
    public ApiResponse<CreateMemberResponse> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        return memberService.createMember(request);
    }
}
