package Itstime.planear.member.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.member.dto.CheckMemberNameRequest;
import Itstime.planear.member.dto.CheckMemberNameResponse;
import Itstime.planear.member.dto.CreateMemberRequest;
import Itstime.planear.member.dto.CreateMemberResponse;
import Itstime.planear.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 컨트롤러", description = "멤버 정보 관련 API입니다.")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "사용자명 중복 조회", description = "사용자 이름 중복 여부 확인 API")
    @PostMapping("/user/check")
    public ApiResponse<CheckMemberNameResponse> checkMemberName(
            @RequestBody CheckMemberNameRequest request
    ) {
        return memberService.checkMemberName(request);
    }

    @Operation(summary = "사용자명 저장", description = "사용자 생성 및 사용자명 저장 API")
    @PostMapping("/user")
    public ApiResponse<CreateMemberResponse> createMember(
            @RequestBody CreateMemberRequest request
    ) {
        return memberService.createMember(request);
    }
}
