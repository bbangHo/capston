package com.example.capstone.item;

import com.example.capstone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GroupPurchaseItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "groupPurchaseItem")
    private Item item;

    private Integer targetQuantity;

    private Integer discountPrice;
}