package Itstime.planear.shop.dto.process;

import Itstime.planear.shop.domain.BodyPart;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WearingItemProcessDto {

    private Long id;
    private String url;
    private Long categoryId;

    @Builder
    public WearingItemProcessDto(Long id, String url, Long categoryId) {
        this.id = id;
        this.url = url;
        this.categoryId = categoryId;
    }
}
