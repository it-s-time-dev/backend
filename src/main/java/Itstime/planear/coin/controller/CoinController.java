package Itstime.planear.coin.controller;

import Itstime.planear.coin.dto.CoinAmountResponse;
import Itstime.planear.coin.dto.UpdateCoinAmountRequest;
import Itstime.planear.coin.service.CoinService;
import Itstime.planear.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "코인 조회 컨트롤러", description = "코인 조회 API입니다.")
@RequiredArgsConstructor
@RestController
public class CoinController {
    private final CoinService coinService;

    @GetMapping("/user/coin")
    @Operation(summary = "멤버 코인 조회", description = "사용자별 보유 코인 조회 API")
    public ApiResponse<CoinAmountResponse> getCoinAmount(@RequestHeader(name = "user-no", required = false) Long memberId) {
        return ApiResponse.success(coinService.getCoinAmount(memberId));
    }

    @PostMapping("/user/coin")
    @Operation(summary = "멤버별 코인 개수 설정", description = "사용자별 코인 설정 API")
    public ApiResponse<CoinAmountResponse> updateCoinAmount(
            @RequestHeader(name = "user-no", required = false) Long memberId,
            @RequestBody UpdateCoinAmountRequest request){

        return coinService.updateCoinAmount(memberId, request);
    }
}
