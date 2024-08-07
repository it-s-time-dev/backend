package Itstime.planear.coin.service;

import Itstime.planear.coin.domain.Coin;
import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.coin.domain.CoinRepository;
import Itstime.planear.coin.dto.CoinAmountResponse;
import Itstime.planear.coin.dto.UpdateCoinAmountRequest;
import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoinService {
    private final CoinRepository coinRepository;
    private final MemberRepository memberRepository;

    public CoinAmountResponse getCoinAmount(Long memberId) {
        if (memberId == null) {
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new PlanearException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST));
        Coin coin = coinRepository.findByMemberId(memberId).orElseGet(() -> coinRepository.save(new Coin(member, 0)));
        return new CoinAmountResponse(coin.getCoinAmount().getAmount());
    }

    @Transactional
    public ApiResponse<CoinAmountResponse> updateCoinAmount(Long memberId, UpdateCoinAmountRequest request) {
        if (memberId == null) {
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new PlanearException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST));
        Coin coin = coinRepository.findByMemberId(memberId).orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        CoinAmount coinAmount = new CoinAmount(request.coinAmount());
        coin.updateCoinAmount(coinAmount);
        return ApiResponse.success(new CoinAmountResponse(coinAmount.getAmount()));
    }
}
