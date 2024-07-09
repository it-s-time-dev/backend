package Itstime.planear.shop.dto.process;

import Itstime.planear.shop.domain.BodyPart;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyItemProcessDto {

    private Long id;
    private String url_shop;
    private String url_avatar1;
    private String url_avatar2;
    private BodyPart bodyPart;


    @Builder
    public MyItemProcessDto(Long id, String url_shop, String url_avatar1, String url_avatar2, BodyPart bodyPart) {
        this.id = id;
        this.url_shop = url_shop;
        this.url_avatar1 = url_avatar1;
        this.url_avatar2 = url_avatar2;
        this.bodyPart = bodyPart;
    }

}
