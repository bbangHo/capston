package com.example.capstone.review;

import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.item.Item;
import com.example.capstone.item.Review;
import com.example.capstone.item.dto.ReviewResponseDTO;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.item.repository.ReviewRepository;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.item.converter.ReviewConverter.toReviewList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DOUBLE;

@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ItemRepository itemRepository;

    private final List<Review> reviews = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    List<Item> itemsWithId;


    @BeforeEach
        // 테스트 실행 전 실행
    void setup() {
        log.info("=============================== setup ==============================");

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

        itemRepository.saveAll(items);

        itemsWithId = itemRepository.findAll();


        for (int i = 1; i < 31; i++) {
            Review review = Review.builder()
                    .score((double) i % 5)
                    .content("testContent" + i)
                    .item(itemsWithId.get((i%5)))
                    .build();
            reviews.add(review);
        }


        reviewRepository.saveAll(reviews);

    }

    @DisplayName("특정 상품의 리뷰 목록을 조회")
    @Test
    void searchReviews() {

        //given

        // when
        Page<Review> reviewList = reviewRepository.findByItemId(itemsWithId.get(0).getId(), PageRequest.of(0, 6, Sort.by("createdAt").descending()));

        // then
        assertThat(reviewList.getContent().size()).isEqualTo(6);
    }

    @DisplayName("리뷰 평균 조회")
    @Test
    void searchAverageOfReviews() {


        //given

        // when
        Double averageScore = reviewRepository.getAverageScore();

        // then
        assertThat(averageScore).isEqualTo(reviews.stream().mapToDouble(Review::getScore).average().orElseThrow());
    }


}
