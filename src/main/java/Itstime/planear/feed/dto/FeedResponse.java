package Itstime.planear.feed.dto;

import Itstime.planear.shop.domain.BodyPart;

import java.util.List;

public record FeedResponse(
        String nickname,
        List<FeedWearingResponse> items,
        FeedStatusMessageResponse statusMessage
) {

    public record FeedWearingResponse(
            Long id,
            String url,
            BodyPart bodyPart
    ) {
    }
}
