package Itstime.planear.shop.dto.response;

import Itstime.planear.shop.dto.process.MyItemProcessDto;
import lombok.Getter;

import java.util.List;

@Getter
public class MyItemResponseDto {

    private List<MyItemProcessDto> items;

    public MyItemResponseDto(List<MyItemProcessDto> items) {
        this.items = items;
    }
}
