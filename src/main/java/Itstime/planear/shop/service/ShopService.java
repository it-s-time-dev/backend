package Itstime.planear.shop.service;


import Itstime.planear.coin.domain.Coin;
import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.coin.domain.CoinRepository;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.shop.domain.BodyPart;
import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Purchase;
import Itstime.planear.shop.domain.Wearing;
import Itstime.planear.shop.dto.process.ItemListProcessDto;
import Itstime.planear.shop.dto.process.MyItemProcessDto;
import Itstime.planear.shop.dto.process.WearingItemProcessDto;
import Itstime.planear.shop.dto.request.ApplyItemRequestDto;
import Itstime.planear.shop.dto.request.BuyItemRequestDto;
import Itstime.planear.shop.dto.request.CreateItemRequestDto;
import Itstime.planear.shop.dto.request.SaveImgUrlRequestDto;
import Itstime.planear.shop.dto.response.*;
import Itstime.planear.shop.repository.ItemRepository;
import Itstime.planear.shop.repository.PurchaseRepository;
import Itstime.planear.shop.repository.WearingRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final WearingRepsitory wearingRepsitory;
    private final PurchaseRepository purchaseRepository;
    private final CoinRepository coinRepository;

    // 부위별 아이템 목록 조회
    public ApiResponse<ItemListResponseDto> itemListByCategory(Long categoryId, Long memberId){
        if(!existByMemberId(memberId)){
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND);
        }
        BodyPart bodyPart = BodyPart.fromValue(categoryId.intValue());
        List<Item> itemList = itemRepository.findByBodyPart(bodyPart);
        // 멤버가 보유한 아이템 목록 조회
        List<Item> purchaseItemList = purchaseRepository.findItemsByMemberId(memberId);
        // 보유한 아이템 ID 정보 hash set 생성
        Set<Long> itemIdSet = purchaseItemList.stream().map(Item::getId).collect(Collectors.toSet());
        List<ItemListProcessDto> listProcessDto = itemList.stream()
                .map(item -> ItemListProcessDto.builder()
                        .id(item.getId())
                        .url_shop(item.getImg_url_shop())
                        .url_avatar1(item.getImg_url_avatar1())
                        .url_avatar2(item.getImg_url_avatar2())
                        .price(item.getPrice())
                        .has(itemIdSet.contains(item.getId())) // 시간복잡도 O(1)
                        .build()).toList();

        return ApiResponse.success(new ItemListResponseDto(listProcessDto));
    }

    private boolean existByMemberId(Long memberId){
        return memberRepository.existsById(memberId);
    } // member 존재여부

    @Transactional
    public ApiResponse<CreateItemResponseDto> createItem(CreateItemRequestDto dto){
        BodyPart bodyPart = BodyPart.fromValue(dto.bodyPart().intValue()); // BodyPart Long -> 객체로 변환
        Item newItem = new Item(dto.price(), bodyPart, dto.img_url_shop(), dto.img_url_avatar1(), dto.img_url_avatar2());
        itemRepository.save(newItem);
        return ApiResponse.success(new CreateItemResponseDto("success"));
    }

    @Transactional
    public ApiResponse<BuyItemResponseDto> buyItem(Long memberId, BuyItemRequestDto dto){
        Member member = checkByMemberId(memberId); // 멤버 확인
        // 상점에 존재하는 아이템인지 확인
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        // 기존에 구매한 아이템인지 구매내역에서 확인
        if (purchaseRepository.existsByMemberIdAndItemId(member.getId(), dto.itemId())){
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        // 보유코인 확인
        Coin coin = coinRepository.findByMemberId(memberId).orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        if (coin.getCoinAmount().getAmount() < item.getPrice()){
            // 보유코인이 가격 미만이면 예외
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        CoinAmount resultCoin = coin.getCoinAmount().minus(item.getPrice());
        coin.updateCoinAmount(resultCoin);
        BodyPart bodyPart = item.getBodyPart();
        purchaseRepository.save(new Purchase(member, item, bodyPart));
        return ApiResponse.success(new BuyItemResponseDto("success"));
    }

    public ApiResponse<WearingItemListResponseDto> wearingItems(Long memberId){
        Member member = checkByMemberId(memberId); // 멤버 확인
        List<Wearing> wearingList = wearingRepsitory.findByMemberId(member.getId());
        List<WearingItemProcessDto> responseDto = wearingList.stream().map(wearing -> WearingItemProcessDto.builder()
                .id(wearing.getItem().getId())
                .url_shop(wearing.getItem().getImg_url_shop())
                .url_avatar1(wearing.getItem().getImg_url_avatar1())
                .url_avatar2(wearing.getItem().getImg_url_avatar2())
                .bodyPart(wearing.getBodyPart())
                .build()).toList();
        return ApiResponse.success(new WearingItemListResponseDto(responseDto));
    }

    @Transactional
    public ApiResponse<ApplyItemResponseDto> applyItem(Long memberId, ApplyItemRequestDto dto){
        Member member = checkByMemberId(memberId); // 멤버 확인
        Item item = itemRepository.findById(dto.itemId()) // 아이템 확인
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        if (!purchaseRepository.existsByMemberIdAndItemId(member.getId(), item.getId())){ // 구매 내역 확인
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND);
        }
        Optional<Wearing> wearingItem = wearingRepsitory.findByMemberIdAndBodyPart(member.getId(), item.getBodyPart());
        if (wearingItem.isEmpty()){
            wearingRepsitory.save(new Wearing(member, item, item.getBodyPart()));
        }else {
            Wearing wearing = wearingItem.get();
            wearing.updateWearingItem(item);
            wearingRepsitory.save(wearing);
        }
        return ApiResponse.success(new ApplyItemResponseDto("success"));
    }

    private Member checkByMemberId(Long memberId){ // member 객체 검색, 응집성 고려하여 메소드 위치이동
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ApiResponse<CommonResponseDto> updateImgUrl(SaveImgUrlRequestDto dto){
        Item item = itemRepository.findById(dto.itemId()).orElseThrow(
                () -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        item.updateImg_url(dto.url());
        return ApiResponse.success(new CommonResponseDto("success"));
    }
}
