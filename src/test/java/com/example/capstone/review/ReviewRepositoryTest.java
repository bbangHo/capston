package com.example.capstone.review;

import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.item.Item;
import com.example.capstone.item.Review;
import com.example.capstone.item.dto.ReviewResponseDTO;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.item.repository.ReviewRepository;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.item.converter.ReviewConverter.toReviewList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DOUBLE;

@DataJpaTest
@Import(QueryDslConfig.class)
public class ReviewRepositoryTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReviewRepositoryTest.class);
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    private final List<Review> reviews = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final List<OrderItem> orderItems = new ArrayList<>();


    @BeforeEach
        // 테스트 실행 전 실행
    void setup() {
        log.info("=============================== setup ==============================");

        for (int i = 1; i < 31; i++) {
            Item item = Item.builder()
                    .id((long) ((i % 3) + 1))
                    .name("name" + i)
                    .price(1000)
                    .deliveryCharge(3000)
                    .stock(200)
                    .itemDetailsImageUrl("URL")
                    .deadline(LocalDateTime.now().plusDays(1))
                    .build();
            items.add(item);
        }

        for (int i = 1; i < 31; i++) {
            OrderItem orderItem = OrderItem.builder()
                    .id((long) i)
                    .build();

            orderItems.add(orderItem);

        }

        for (int i = 1; i < 31; i++) {
            Review review = Review.builder()
                    .id((long) i)
                    .score((double) i % 5)
                    .content("testContent" + i)
                    .item(items.get(i - 1))
                    .orderItem(orderItems.get(i - 1))
                    .build();
            reviews.add(review);
        }

        orderItemRepository.saveAll(orderItems);
        itemRepository.saveAll(items);
        reviewRepository.saveAll(reviews);

    }

    @DisplayName("특정 상품의 리뷰 목록을 조회")
    @Test
    void searchReviews() {

        //given

        // when
        ReviewResponseDTO.ReviewList reviewList = toReviewList(reviewRepository.findByItemId(1L, PageRequest.of(0, 6, Sort.by("createdAt").descending())));

        // then
        assertThat(reviewList.getReviewList().size()).isEqualTo(6);
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
