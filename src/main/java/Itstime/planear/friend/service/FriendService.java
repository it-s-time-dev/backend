package Itstime.planear.friend.service;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.friend.domain.Friend;
import Itstime.planear.friend.domain.FriendRepository;
import Itstime.planear.friend.dto.FriendResponseDto;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public ApiResponse<FriendResponseDto.AddFriendResponse> addFriend(Long memberId, String memberCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Member friendMember = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Friend friend = new Friend(member,friendMember);
        friendRepository.save(friend);
        return ApiResponse.success(new FriendResponseDto.AddFriendResponse("SUCESS"));
    }
}
