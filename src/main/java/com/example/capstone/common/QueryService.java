package com.example.capstone.common;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.member.repository.SubscriptionRepository;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.OrderItemRepository;
import com.example.capstone.post.Post;
import com.example.capstone.post.repository.PostRepository;
import com.example.capstone.seller.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueryService {
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * ROLE이 Seller인지 검사하고 Seller 객체를 반환합니다.
     *
     * @param memberId 사용자 ID
     * @return Seller 객체
     */
    public Seller isSeller(Long memberId) {
        Member member = findMember(memberId);
        if (member.getType() == MemberType.ROLE_CONSUMER) {
            throw new GeneralException(ErrorStatus.SELLER_UNAUTHORIZED);
        }

        return member.getSeller();
    }

    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_NOT_FOUND));
    }

    public Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));
    }

    public Boolean categoryIsPresent(Long categoryId) {
        return Optional.ofNullable(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND)))
                        .isPresent();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public Boolean memberIsPresent(Long memberId) {
        return Optional.ofNullable(memberRepository.findById(memberId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)))
                .isPresent();
    }

    public Boolean isSubscribe(Long fromMemberId, Long toMemberId) {
        Optional<Subscription> subscriptionOptional = Optional.ofNullable(
                subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMemberId, toMemberId));

        return subscriptionOptional.isPresent();
    }

    public Subscription findSubscribe(Long fromMemberId, Long toMemberId) {
        Optional<Subscription> subscriptionOptional = Optional.ofNullable(
                subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMemberId, toMemberId));

        return subscriptionOptional.orElse(null);
    }

    public Subscription findSubscription(Long fromMemberId, Long toMemberId) {
        Optional<Subscription> subscriptionOptional = Optional.ofNullable(
                subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMemberId, toMemberId));

        return subscriptionOptional.orElseThrow(() -> new GeneralException(ErrorStatus.ALREADY_UNSUBSCRIBED));
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }
}
