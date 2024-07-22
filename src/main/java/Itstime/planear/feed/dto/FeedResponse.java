package Itstime.planear.feed.dto;

import Itstime.planear.shop.domain.BodyPart;

import java.time.LocalDateTime;
import java.util.List;

public record FeedResponse(
        String nickname,
        List<FeedWearingResponse> items,
        FeedStatusMessageResponse statusMessage,
        LocalDateTime updatedAt,
        String updateTimeMessage
) {

    public record FeedWearingResponse(
            Long id,
            String url_shop,
            String url_avatar1,
            String url_avatar2,
            BodyPart bodyPart
    ) {
    }
}
