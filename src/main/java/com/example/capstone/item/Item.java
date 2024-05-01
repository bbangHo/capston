package com.example.capstone.item;

import com.example.capstone.common.BaseEntity;
import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.item.common.ItemType;
import com.example.capstone.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @NotNull
    private String name;

    private String simpleExplanation;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    @Min(0)
    private Integer deliveryCharge;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private String ItemDetailsImageUrl;

    @NotNull
    @Future
    @ColumnDefault("'2024-12-31T00:00:00.000000'")
    @Builder.Default
    private LocalDateTime deadline = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "group_purchase_item_id")
    private GroupPurchaseItem groupPurchaseItem;


    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Inquiry> inquiries = new ArrayList<>();

    public void addItemImage(ItemImage itemImage) {
        itemImages.add(itemImage);
        itemImage.setItem(this);
    }

    public void addItemInquiry(Inquiry inquiry) {
        inquiries.add(inquiry);
        inquiry.setItem(this);
    }
}
