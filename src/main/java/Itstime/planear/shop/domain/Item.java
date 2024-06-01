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

    private String img_url; // s3 url

    public Item(int price, BodyPart bodyPart, String img_url) {
        this.price = price;
        this.bodyPart = bodyPart;
        this.img_url = img_url;
    }

    public void updateImg_url(String url){
        this.img_url = url;
    }
}
