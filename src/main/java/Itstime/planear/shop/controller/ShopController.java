package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.shop.dto.request.CreateItemRequestDto;
import Itstime.planear.shop.dto.response.CreateItemResponseDto;
import Itstime.planear.shop.dto.response.ItemListResponseDto;
import Itstime.planear.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상점 컨트롤러", description = "상점 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/avatar")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/store/{categoryId}")
    @Operation(summary = "카테고리별 아이템 목록 조회", description = "FACE(1), HAIR(2), TOP(3), BOTTOM(4), SHOES(5), ACCESSORY(6)")
    public ApiResponse<ItemListResponseDto> itemListByCategory(
            @PathVariable(name = "categoryId", required = true) Long categoryId,
            @RequestHeader(name = "user-no", required = false) Long memberId
    ){
        return shopService.itemListByCategory(categoryId, memberId);
    }

    @PostMapping("/store")
    @Operation(summary = "카테고리별 아이템 추가", description = "FACE(1), HAIR(2), TOP(3), BOTTOM(4), SHOES(5), ACCESSORY(6)")
    public ApiResponse<CreateItemResponseDto> createItem(
            @RequestBody @Valid CreateItemRequestDto dto
            ){
        return shopService.createItem(dto);
    }


}
