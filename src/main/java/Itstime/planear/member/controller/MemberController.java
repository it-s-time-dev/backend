package Itstime.planear.member.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.member.dto.*;
import Itstime.planear.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "닉네임으로 ID 조회", description = "창우 주문사항1 : 닉네임으로 멤버ID 조회하기 API")
    @GetMapping("/user/find")
    public ApiResponse<FindMemberResponse> findMember(
            @RequestParam(name = "name") String name
    ){
        return memberService.findMember(name);
    }
}
