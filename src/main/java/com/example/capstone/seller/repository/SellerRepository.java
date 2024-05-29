package com.example.capstone.seller.repository;

import com.example.capstone.seller.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select s from Seller s " +
            "join Subscription sub on s.id = sub.toMember.id " +
            "join Member m on sub.fromMember.id = m.id " +
            "where m.id = :memberId")
    Page<Seller> findSubscribedSeller(Long memberId, Pageable pageable);

}
