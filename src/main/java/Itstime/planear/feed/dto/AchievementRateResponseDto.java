package Itstime.planear.feed.dto;

import Itstime.planear.shop.domain.BodyPart;

import java.util.List;

public record AchievementRateResponseDto(
        String nickname,
        List<AchievementRateItemUrlProcessDto> items,
        int achievementRate,
        int todayScheduleCount,
        List<FriendInfo> friendInfos
) {

    public record AchievementRateItemUrlProcessDto(
            Long id,
            String url_shop,
            String url_avatar1,
            String url_avatar2,
            BodyPart bodyPart
    ){}

    public record FriendInfo(
            String nickname,
            List<AchievementRateResponseDto.AchievementRateItemUrlProcessDto> items,
            int achievementRate,
            int todayScheduleCount
    ){}
}