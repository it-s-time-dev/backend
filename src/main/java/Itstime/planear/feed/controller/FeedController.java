package Itstime.planear.feed.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.feed.dto.AchievementRateResponseDto;
import Itstime.planear.feed.dto.FeedsResponse;
import Itstime.planear.feed.service.FeedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "피드 컨트롤러", description = "피드 관련 API입니다.")
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
