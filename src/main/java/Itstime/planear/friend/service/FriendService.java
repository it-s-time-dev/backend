package Itstime.planear.friend.service;
import Itstime.planear.coin.domain.Coin;
import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.coin.domain.CoinRepository;
import Itstime.planear.coin.dto.CoinAmountResponse;
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
    private final CoinRepository coinRepository;

    public ApiResponse<FriendResponseDto> addFriend(Long memberId, String memberCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Member friendMember = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        Coin coin = coinRepository.findByMemberId(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        // 친구 여부 확인
        boolean friendExists = friendRepository.existsByMemberIdAndFriendMemberId(memberId, friendMember.getId());
        if (friendExists) {
            throw new PlanearException("이미 추가된 친구입니다.", HttpStatus.CONFLICT);
        }

        Friend newFriend = new Friend(member,friendMember);

        CoinAmount resultCoin = coin.getCoinAmount().add(10);
        coin.updateCoinAmount(resultCoin);

        friendRepository.save(newFriend);
        CoinAmountResponse coinAmountResponse = new CoinAmountResponse(resultCoin.getAmount());

        return ApiResponse.success(new FriendResponseDto("SUCCESS",coinAmountResponse));
    }
    public ApiResponse<ShowFriendResponseDto> showFriend(String memberCode) {
        Member friendMember = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        List<Wearing> wearingList = wearingRepsitory.findAllByMemberId(friendMember.getId());

        List<MyItemProcessDto> itemDto = wearingList.stream()
                .map(wearing -> new MyItemProcessDto(
                        wearing.getId(),
                        wearing.getItem().getImg_url_shop(),
                        wearing.getItem().getImg_url_avatar1(),
                        wearing.getItem().getImg_url_avatar2(),
                        wearing.getBodyPart()))
                .collect(Collectors.toList());

        ShowFriendResponseDto result = new ShowFriendResponseDto(friendMember.getMemberName().getName(), itemDto);

        return ApiResponse.success(result);
    }
}
