package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.shop.dto.response.ItemListResponseDto;
import Itstime.planear.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avatar")
public class ShopController {

    private final ShopService shopService;


    // 카테고리별 아이템 목록 조회
    @GetMapping("/store/{categoryId}")
    public ApiResponse<ItemListResponseDto> itemListByCategory(
            @PathVariable(name = "categoryId", required = true) Long categoryId,
            @RequestHeader(name = "user-no", required = false) Long memberId
    ){
        return shopService.itemListByCategory(categoryId, memberId);
    }


}
