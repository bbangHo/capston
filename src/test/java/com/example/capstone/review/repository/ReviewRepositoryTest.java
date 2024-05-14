package com.example.capstone.review.repository;

import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.item.Item;
import com.example.capstone.item.Review;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.item.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("특정 상품의 리뷰 목록을 조회")
    @Test
    void searchReviews() {

        //given
        itemRepository.saveAll(getItems());
        List<Item> itemsWithId = itemRepository.findAll();
        reviewRepository.saveAll(getReviews(itemsWithId));

        // when
        Page<Review> reviewList = reviewRepository.findByItemId(itemsWithId.get(0).getId(), PageRequest.of(0, 6, Sort.by("createdAt").descending()));

        // then
        assertThat(reviewList.getContent().size()).isEqualTo(6);
    }

    @DisplayName("리뷰 평균 조회")
    @Test
    void searchAverageOfReviews() {

        //given
        itemRepository.saveAll(getItems());
        List<Item> itemsWithId = itemRepository.findAll();
        reviewRepository.saveAll(getReviews(itemsWithId));


        // when
        Double averageScore = reviewRepository.getAverageScore(itemsWithId.get(0).getId());

        // then
        assertThat(averageScore).isEqualTo(getReviews(itemsWithId).stream()
                .filter(review -> review.getItem().getId().equals(itemsWithId.get(0).getId()))
                .mapToDouble(Review::getScore)
                .average().orElseThrow());
    }

    private List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Item item = Item.builder()
                    .name("name" + i)
                    .price(1000)
                    .deliveryCharge(3000)
                    .stock(200)
                    .itemDetailsImageUrl("URL")
                    .deadline(LocalDateTime.now().plusDays(1))
                    .build();
            items.add(item);
        }
        return items;
    }

    private List<Review> getReviews(List<Item> itemsWithId){
        List<Review> reviews = new ArrayList<>();

        for (int i = 1; i < 31; i++) {
            Review review = Review.builder()
                    .score((double) i % 5)
                    .content("testContent" + i)
                    .item(itemsWithId.get(i%5))
                    .build();
            reviews.add(review);

        }
        return reviews;
    }


}
