package Itstime.planear.shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price; // 가격

    @Enumerated(EnumType.STRING)
    private BodyPart bodyPart; // 부위

    private String img_url_shop; // 상점 아이템 이미지
    private String img_url_avatar1; // 아바타 아이템 이미지
    private String img_url_avatar2; // 아바타 아이템 이미지

    public Item(int price, BodyPart bodyPart, String img_url_shop, String img_url_avatar1, String img_url_avatar2) {
        this.price = price;
        this.bodyPart = bodyPart;
        this.img_url_shop = img_url_shop;
        this.img_url_avatar1 = img_url_avatar1;
        this.img_url_avatar2 = img_url_avatar2;
    }

    public void updateImg_url(String url){
        this.img_url_shop = url;
    }
}
