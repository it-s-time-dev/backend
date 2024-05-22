package Itstime.planear.coin.controller;

import Itstime.planear.coin.dto.CoinAmountResponse;
import Itstime.planear.coin.service.CoinService;
import Itstime.planear.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CoinController {
    private final CoinService coinService;

    @GetMapping("/user/coin")
    public ApiResponse<CoinAmountResponse> getCoinAmount(@RequestHeader(name = "user-no", required = false) Long memberId) {
        return ApiResponse.success(coinService.getCoinAmount(memberId));
    }
}
