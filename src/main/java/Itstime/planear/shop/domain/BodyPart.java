package Itstime.planear.shop.domain;

import Itstime.planear.exception.PlanearException;
import org.springframework.http.HttpStatus;

public enum BodyPart {
    FACE(1), HAIR(2), TOP(3), BOTTOM(4), SHOES(5), ACCESSORY(6);

    private final int value;

    BodyPart(int value) {
        this.value = value;
    }

    public static BodyPart fromValue(int value) {
        for (BodyPart part : values()) {
            if (part.value == value) {
                return part;
            }
        }
        throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND);
    }

    public int getValue(){
        return this.value;
    }
}
