package com.example.capstone.item.repository;

import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.item.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByItemId(Long itemId, Pageable pageable);

    @Query("select avg(r.score) from Review r")
    Double getAverageScore();

}
