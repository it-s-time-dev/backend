package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.shop.dto.request.ApplyItemRequestDto;
import Itstime.planear.shop.dto.request.BuyItemRequestDto;
import Itstime.planear.shop.dto.request.CreateItemRequestDto;
import Itstime.planear.shop.dto.response.*;
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

    @PostMapping("/store/buy")
    @Operation(summary = "아이템 구매", description = "FACE(1), HAIR(2), TOP(3), BOTTOM(4), SHOES(5), ACCESSORY(6)")
    public ApiResponse<BuyItemResponseDto> buyItem(
            @RequestHeader(name = "user-no", required = false) Long memberId,
            @RequestBody @Valid BuyItemRequestDto dto
    ){
        return shopService.buyItem(memberId, dto);
    }

    @GetMapping("/store/me")
    @Operation(summary = "내 아이템 카테고리별 조회", description = "FACE(1), HAIR(2), TOP(3), BOTTOM(4), SHOES(5), ACCESSORY(6)")
    public ApiResponse<MyItemResponseDto> myItemByCategoryId(
            @RequestParam(name = "categoryId") Long categoryId,
            @RequestHeader(name = "user-no") Long memberId
    ){
        return shopService.myItemByCategoryId(memberId,categoryId);
    }

    @GetMapping("/me")
    @Operation(summary = "현재 입고있는 아이템 목록 조회", description = "현재 입고있는 부위별 아이템 목록 조회 api")
    public ApiResponse<WearingItemListResponseDto> wearingItems(
            @RequestHeader(name = "user-no") Long memberId
    ){
        return shopService.wearingItems(memberId);
    }

    @PostMapping("/me/wear")
    @Operation(summary = "아이템 착용하기", description = "아이템 착용하기")
    public ApiResponse<ApplyItemResponseDto> applyItem(
            @RequestHeader(name = "user-no") Long memberId,
            @RequestBody ApplyItemRequestDto dto
    ){
        return shopService.applyItem(memberId, dto);
    }
}
