package Itstime.planear.shop.dto.request;

import jakarta.validation.constraints.NotNull;

public record BuyItemRequestDto(
        @NotNull Long itemId) {
}
