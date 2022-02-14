package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor; // Predicate란 ‘이 조건이 맞다’고 판단하는 근거를 함수로 제공하는 것


public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    List<Item> findByItemNm(String itemNm);
    List<Item> findByItemNmOrItemDetail(String nm, String desc);
    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);
    List<Item> findByPriceGreaterThanAndPriceLessThan(Integer minPrice, Integer maxPrice);
    List<Item> findByItemNmLike(String itemNm);
    List<Item> findByItemNmStartingWith(String itemNm);

    /*
    @Param 어노테이션을 이용하여 변수를 JPQL에 전달하는 대신 파라미터의 순서를 이용해 전달해줄 수도 있습니다.
     그럴 경우 ‘:itemDetail’ 대신 첫 번째 파라미터를 전달하겠다는 ‘?1’이라는 표현을 사 용하면 됩니다.
      하지만 파라미터의 순서가 달라지면 해당 쿼리문이 제대로 동작하지 않을 수 있기 때문에
      좀 더 명시적인 방법인 @Param 어노테이션을 이용하는 방법을 추천합니다.
    */
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    /*
    복잡한 쿼리의 경우 @Query 어노테이션을 사용
    기존의 데이터베이스에서 사용하는 쿼리 그대로 사용해야 할 때는 @Query 의 nativeQuery 속성을 사용
    이 경우 특정 데이터베이스에 종속되는 쿼리문을 사용하기 때문에 데이터베이스 종속성이 발생함
    복잡한 쿼리를 사용할 때만 활용을 권장
    */
    @Query(value="SELECT * FROM Item i WHERE i.item_detail LIKE %:itemDetail% ORDER BY i.price DESC", nativeQuery=true)
    List<Item> findByItemDetailByNative(@Param("itemDetail" ) String itemDetail);



}
