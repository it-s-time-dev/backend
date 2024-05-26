package Itstime.planear.shop.dto.response;

import Itstime.planear.shop.dto.process.ItemListProcessDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemListResponseDto {

    private List<ItemListProcessDto> items;

    public ItemListResponseDto(List<ItemListProcessDto> items) {
        this.items = items;
    }


}
