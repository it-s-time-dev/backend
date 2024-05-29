package Itstime.planear.shop.dto.process;

import Itstime.planear.shop.domain.BodyPart;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyItemProcessDto {

    private Long id;
    private String url;
    private BodyPart bodyPart;

    @Builder
    public MyItemProcessDto(Long id, String url, BodyPart bodyPart) {
        this.id = id;
        this.url = url;
        this.bodyPart = bodyPart;
    }
}
