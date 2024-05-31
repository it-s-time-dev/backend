package Itstime.planear.shop.dto.response;

import Itstime.planear.shop.dto.process.WearingItemProcessDto;

import java.util.List;

public record WearingItemListResponseDto(List<WearingItemProcessDto> items) {
}
