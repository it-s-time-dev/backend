package Itstime.planear.shop.dto.process;


import lombok.Builder;
import lombok.Getter;

@Getter

public class ItemListProcessDto {

    private Long id;
    private String url_shop;
    private String url_avatar1;
    private String url_avatar2;
    private int price;
    private boolean has;

    @Builder
    public ItemListProcessDto(Long id, String url_shop, String url_avatar1, String url_avatar2, int price, boolean has) {
        this.id = id;
        this.url_shop = url_shop;
        this.url_avatar1 = url_avatar1;
        this.url_avatar2 = url_avatar2;
        this.price = price;
        this.has = has;
    }
}
