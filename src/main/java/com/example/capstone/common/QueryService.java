package com.example.capstone.common;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.member.repository.SubscriptionRepository;
import com.example.capstone.post.Post;
import com.example.capstone.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueryService {
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;

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
