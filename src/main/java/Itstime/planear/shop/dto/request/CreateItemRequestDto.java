package Itstime.planear.shop.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateItemRequestDto(
         @Min(0) int price, // 0원 이상
         @NotNull Long bodyPart,
         @NotNull String img_url_shop,
         @NotNull String img_url_avatar1,
         @NotNull String img_url_avatar2
         ) {
}
