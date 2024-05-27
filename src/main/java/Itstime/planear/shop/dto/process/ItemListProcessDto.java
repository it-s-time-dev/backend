package Itstime.planear.shop.dto.process;


import lombok.Builder;
import lombok.Getter;

@Getter

public class ItemListProcessDto {

    private Long id;
    private String url;
    private int price;
    private boolean has;

    @Builder
    public ItemListProcessDto(Long id, String url, int price, boolean has) {
        this.id = id;
        this.url = url;
        this.price = price;
        this.has = has;
    }
}
