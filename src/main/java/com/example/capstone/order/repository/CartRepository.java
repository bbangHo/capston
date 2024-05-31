package com.example.capstone.order.repository;

import com.example.capstone.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select count(*) from Cart c where c.member.id = :memberId")
    Integer countAllItemInCart(Long memberId);

    @Query("select c from Cart c " +
            "join Item i on c.item.id = i.id " +
            "join Seller s on s.id = i.seller.id " +
            "where c.member.id = :memberId " +
            "group by c.item.id")
    List<Cart> searchItemInCart(Long memberId);


}
