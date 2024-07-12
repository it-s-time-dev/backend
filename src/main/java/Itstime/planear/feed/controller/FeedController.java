package Itstime.planear.feed.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.feed.dto.AchievementRateResponseDto;
import Itstime.planear.feed.dto.FeedsResponse;
import Itstime.planear.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/feed")
    public ApiResponse<FeedsResponse> getFeed(
            @RequestHeader(name = "user-no") Long memberId
    ) {
        return ApiResponse.success(feedService.getFeed(memberId));
    }

    @GetMapping("/achievementRate")
    public ApiResponse<AchievementRateResponseDto> achievementRate(
            @RequestHeader(name = "user-no", required = false) Long memberId
    ){
        return ApiResponse.success(feedService.achievementRate(memberId));
    }
}
