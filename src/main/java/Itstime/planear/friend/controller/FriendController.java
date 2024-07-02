package Itstime.planear.friend.controller;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.friend.dto.FriendResponseDto;
import Itstime.planear.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/friends/add")
    public ApiResponse<FriendResponseDto.AddFriendResponse> addFriend(@RequestHeader(value = "user-no", required = false)Long memberId, String memberCode) {
        return friendService.addFriend(memberId,memberCode);
    }
}
