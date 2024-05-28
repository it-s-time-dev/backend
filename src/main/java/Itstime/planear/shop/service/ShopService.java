package Itstime.planear.shop.service;


import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.shop.domain.BodyPart;
import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Purchase;
import Itstime.planear.shop.dto.process.ItemListProcessDto;
import Itstime.planear.shop.dto.process.MyItemProcessDto;
import Itstime.planear.shop.dto.request.BuyItemRequestDto;
import Itstime.planear.shop.dto.request.CreateItemRequestDto;
import Itstime.planear.shop.dto.response.BuyItemResponseDto;
import Itstime.planear.shop.dto.response.CreateItemResponseDto;
import Itstime.planear.shop.dto.response.ItemListResponseDto;
import Itstime.planear.shop.dto.response.MyItemResponseDto;
import Itstime.planear.shop.repository.ItemRepository;
import Itstime.planear.shop.repository.PurchaseRepository;
import Itstime.planear.shop.repository.WearingRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final WearingRepsitory wearingRepsitory;
    private final PurchaseRepository purchaseRepository;

    public Member checkByMemberId(Long memberId){ // member 객체 검색
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
    }

    public boolean existByMemberId(Long memberId){
        return memberRepository.existsById(memberId);
    } // member 존재여부

    // 부위별 아이템 목록 조회
    public ApiResponse<ItemListResponseDto> itemListByCategory(Long categoryId, Long memberId){
        if(!existByMemberId(memberId)){
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND);
        }
        else{
            BodyPart bodyPart = BodyPart.fromValue(categoryId.intValue());
            List<Item> itemList = itemRepository.findByBodyPart(bodyPart);
            List<ItemListProcessDto> listProcessDto = itemList.stream()
                    .map(item -> ItemListProcessDto.builder()
                            .id(item.getId())
                            .url(item.getImg_url())
                            .price(item.getPrice())
                            .has(wearingRepsitory.existsByMemberIdAndItem(memberId, item))  // 추후 코드 리팩토링
                            .build()).collect(Collectors.toList());
        return ApiResponse.success(new ItemListResponseDto(listProcessDto));
        }
    }

    public ApiResponse<CreateItemResponseDto> createItem(CreateItemRequestDto dto){
        BodyPart bodyPart = BodyPart.fromValue(dto.bodyPart().intValue()); // BodyPart Long -> 객체로 변환
        Item newItem = new Item(dto.price(), bodyPart, dto.img_url());
        itemRepository.save(newItem);
        return ApiResponse.success(new CreateItemResponseDto("success"));
    }

    public ApiResponse<BuyItemResponseDto> buyItem(Long memberId, BuyItemRequestDto dto){
        Member member = checkByMemberId(memberId); // 멤버 확인
        // 상점에 존재하는 아이템인지 확인
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        // 기존에 구매한 아이템인지 구매내역에서 확인
        if (purchaseRepository.existsByMemberIdAndItemId(member.getId(), dto.itemId())){
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        BodyPart bodyPart = item.getBodyPart();
        purchaseRepository.save(new Purchase(member, item, bodyPart));
        return ApiResponse.success(new BuyItemResponseDto("success"));
    }

    public ApiResponse<MyItemResponseDto> myItemByCategoryId(Long memberId, Long categoryId){
        Member member = checkByMemberId(memberId); // 멤버 확인
        // 유효한 카테고리(부위) 인지 확인
        BodyPart bodyPart = BodyPart.fromValue(categoryId.intValue());
        List<Purchase> myItemList = purchaseRepository.findByMemberIdAndBodyPart(member.getId(), bodyPart);
        List<MyItemProcessDto> myItemResponseDto = myItemList.stream().map(purchase ->
                MyItemProcessDto.builder()
                        .id(purchase.getItem().getId())
                        .url(purchase.getItem().getImg_url())
                        .bodyPart(purchase.getBodyPart())
                        .build()).collect(Collectors.toList());
        return ApiResponse.success(new MyItemResponseDto(myItemResponseDto));
    }

}
