package Itstime.planear.friend.dto;

import Itstime.planear.member.domain.Member;
import Itstime.planear.shop.dto.process.MyItemProcessDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ShowFriendResponseDto {
    String nickname;
    private List<MyItemProcessDto> wearingLists;

    public ShowFriendResponseDto(Member member) {
        this.nickname = member.getMemberName().getName();
        this.wearingLists = member.getWearingList().stream()
                .map(wearing -> new MyItemProcessDto(wearing.getId(), wearing.getItem().getImg_url(), wearing.getBodyPart()))
                .collect(Collectors.toList());
    }
}
