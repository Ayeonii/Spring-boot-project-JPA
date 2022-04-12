package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
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
public class Item extends BaseEntity{

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

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int storkNumber){
        int restStock = this.stockNumber - storkNumber; // 1. 상품의 재고 수량에서 주문 후 남은 재고 수량을 구한다.
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock; // 상품 주문 후 남은 재고 수량을 상품의 현재 재고 값으로 할당합니다.
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }
}

