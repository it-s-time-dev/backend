package Itstime.planear.member.domain;

import Itstime.planear.exception.PlanearException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberName {

    private String name;

    public MemberName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new PlanearException("이름이 비어있습니다.", HttpStatus.BAD_REQUEST);
        }
        if (name.length() > 100) {
            throw new PlanearException("이름은 50자 이하여야 합니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public String getName() {
        return name;
    }
}
