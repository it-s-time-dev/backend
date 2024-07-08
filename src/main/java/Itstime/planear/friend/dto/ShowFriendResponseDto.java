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

    public ShowFriendResponseDto(String nickname, List<MyItemProcessDto> wearingLists) {
        this.nickname = nickname;
        this.wearingLists = wearingLists;
    }
}
