package Itstime.planear.friend.service;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.friend.domain.Friend;
import Itstime.planear.friend.domain.FriendRepository;
import Itstime.planear.friend.dto.FriendResponseDto;
import Itstime.planear.friend.dto.ShowFriendResponseDto;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.shop.domain.Wearing;
import Itstime.planear.shop.dto.process.MyItemProcessDto;
import Itstime.planear.shop.repository.WearingRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final WearingRepsitory wearingRepsitory;

    public ApiResponse<FriendResponseDto> addFriend(Long memberId, String memberCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Member friendMember = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Friend friend = new Friend(member,friendMember);
        friendRepository.save(friend);
        return ApiResponse.success(new FriendResponseDto("SUCCESS"));
    }
    public ApiResponse<ShowFriendResponseDto> showFriend(String memberCode) {
        Member friendMember = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        List<Wearing> wearingList = wearingRepsitory.findAllByMemberId(friendMember.getId());

        List<MyItemProcessDto> itemDto = wearingList.stream()
                .map(wearing -> new MyItemProcessDto(wearing.getId(), wearing.getItem().getImg_url(), wearing.getBodyPart()))
                .collect(Collectors.toList());

        ShowFriendResponseDto result = new ShowFriendResponseDto(friendMember.getMemberName().getName(), itemDto);

        return ApiResponse.success(result);
    }
}
