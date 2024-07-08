package Itstime.planear.friend.controller;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.friend.dto.FriendResponseDto;
import Itstime.planear.friend.dto.ShowFriendResponseDto;
import Itstime.planear.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @Operation(summary = "친구 추가", description = "친구 코드로 친구추가 API")
    @PostMapping("/friends/add")
    public ApiResponse<FriendResponseDto> addFriend(@RequestHeader(value = "user-no", required = false)Long memberId, String memberCode) {
        return friendService.addFriend(memberId,memberCode);
    }
    @Operation(summary = "친구 코드로 프로필 확인", description = "친구 코드로 친구추가시 친구 프로필 보이는 API")
    @GetMapping("member-info")
    public ApiResponse<ShowFriendResponseDto> showFriend(@RequestHeader(value = "user-no",required = false)Long memberId,
                                                         @RequestParam("memberCode")String memberCode) {
        return friendService.showFriend(memberCode);
    }
}
