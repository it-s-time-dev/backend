package Itstime.planear.shop.dto.response;

import Itstime.planear.shop.dto.process.WearingItemProcessDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WearingItemListResponseDto {

    private List<WearingItemProcessDto> items;

    public WearingItemListResponseDto(List<WearingItemProcessDto> items) {
        this.items = items;
    }
}
