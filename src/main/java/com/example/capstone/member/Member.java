package com.example.capstone.member;

import com.example.capstone.common.BaseEntity;
import com.example.capstone.member.common.MemberStatus;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.seller.Seller;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
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
    private MemberType type;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
