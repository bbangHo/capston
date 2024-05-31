package com.example.capstone.member;

import com.example.capstone.common.BaseEntity;
import com.example.capstone.member.common.MemberStatus;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.post.Post;
import com.example.capstone.seller.Seller;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueLoginId", columnNames = {"loginId"}),
                @UniqueConstraint(name = "UniqueNickName", columnNames = {"nickName"})
        }
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String nickName;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberType type = MemberType.ROLE_CONSUMER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberStatus status = MemberStatus.ACTIVITY;

    @OneToOne(mappedBy = "member",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Address address;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orderList = new ArrayList<>();

    public void addSeller(Seller seller) {
        this.seller = seller;
    }

    public void addPost(Post post) {
        this.postList.add(post);
        post.setMember(this);
    }

    public void changeRole() {
        this.type = MemberType.ROLE_SELLER;
    }

    public void changeLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }
    public void changeMember(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

}
