package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for(int i=1; i<=10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품9");

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 조회 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품7", "테스트 상품 상세 설명3");

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 Less Than 테스상품")
    public void findByPriceLessThanTset(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 DESC 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 ASC 테스트")
    public void findByPriceLessThanOrderByPriceAscTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceAsc(10005);

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 Less Greater 테스트")
    public void findByPriceGreaterThanAndPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceGreaterThanAndPriceLessThan(10000, 10004);

        for(Item item : itemList) {
            System.out.print("=====>");
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명 Like")
    public void findByItemNmLikeTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmLike("테스트%");

        for(Item item : itemList) {
            System.out.print("=====>");
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명 starting With")
    public void findByItemNmStartingWithTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmStartingWith("테스트");

        for(Item item : itemList) {
            System.out.print("=====>");
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query 를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

}