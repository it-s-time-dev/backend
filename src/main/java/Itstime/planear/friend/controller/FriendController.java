package Itstime.planear.friend.controller;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.friend.dto.FriendRequestDto;
import Itstime.planear.friend.dto.FriendResponseDto;
import Itstime.planear.friend.dto.ShowFriendResponseDto;
import Itstime.planear.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@Tag(name = "친구 관리 컨트롤러", description = "친구 관리 관련 API입니다.")
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @Operation(summary = "친구 추가", description = "친구 코드로 친구추가 API")
    @PostMapping("/friends/add")
    public ApiResponse<FriendResponseDto> addFriend(@RequestHeader(value = "user-no", required = false)Long memberId, @RequestBody FriendRequestDto dto) {
        return friendService.addFriend(memberId, dto.memberCode());
    }
    @Operation(summary = "친구 코드로 프로필 확인", description = "친구 코드로 친구추가시 친구 프로필 보이는 API")
    @GetMapping("member-info")
    public ApiResponse<ShowFriendResponseDto> showFriend(@RequestHeader(value = "user-no",required = false)Long memberId,
                                                         @RequestParam("memberCode")String memberCode) {
        return friendService.showFriend(memberCode);
    }
}
