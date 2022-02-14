package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item") // @Table 어노테이션을 통해 어떤 테이블과 매핑될지를 지정. item 테이블과 매핑되도록 name을 item으로 지정합니다.
@Getter
@Setter
@ToString
public class Item {

    @Id                                             // 기본키가 되는 멤버변수에 @Id
    @Column(name="item_id")                         // 테이블에 매핑될 컬럼의 이름을 @Column
    @GeneratedValue(strategy = GenerationType.AUTO) // @GeneratedVai나e 어노테이션을 통해 기본키 생성 전략을 AUTO로 지정
    private Long id; // 상품 코드


    @Column(nullable = false, length = 50)          //nullable 속성을 이용해서 항상 값이 있어야 하는 필드는 not null 설정
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정 시간
}
