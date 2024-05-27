package Itstime.planear.shop.service;


import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.shop.domain.BodyPart;
import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.dto.process.ItemListProcessDto;
import Itstime.planear.shop.dto.response.ItemListResponseDto;
import Itstime.planear.shop.repository.ItemRepository;
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


}
