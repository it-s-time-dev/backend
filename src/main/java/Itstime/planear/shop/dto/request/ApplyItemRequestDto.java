package Itstime.planear.shop.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApplyItemRequestDto(
        @NotNull Long itemId) {
}
