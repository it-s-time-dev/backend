package Itstime.planear.coin.domain;

import Itstime.planear.exception.PlanearException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoinAmount {

    private int amount;

    public CoinAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < 0) {
            throw new PlanearException("코인은 음수가 될 수 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    public CoinAmount minus(int amount) {
        return new CoinAmount(this.amount - amount);
    }

    public CoinAmount minus(CoinAmount coinAmount) {
        return new CoinAmount(this.amount - coinAmount.amount);
    }

    public CoinAmount add(int amount) {
        return new CoinAmount(this.amount + amount);
    }
}
