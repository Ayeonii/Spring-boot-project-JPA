package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNm(String itemNm);
    List<Item> findByItemNmOrItemDetail(String nm, String desc);
    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);
    List<Item> findByPriceGreaterThanAndPriceLessThan(Integer minPrice, Integer maxPrice);
    List<Item> findByItemNmLike(String itemNm);
    List<Item> findByItemNmStartingWith(String itemNm);

}