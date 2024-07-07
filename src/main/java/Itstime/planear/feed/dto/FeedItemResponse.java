package Itstime.planear.feed.dto;

import Itstime.planear.shop.domain.BodyPart;

public record FeedItemResponse(
        Long id,
        String url,
        BodyPart bodyPart
) {
}
