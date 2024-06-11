package com.example.capstone.item;

import com.example.capstone.common.BaseEntity;
import com.example.capstone.item.common.ImageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer sequence;

    @NotNull
    private String imageUrl;

    @NotNull
    private String originFileName;

    @Column(unique = true)
    private UUID uuid;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ImageType imageType = ImageType.COMMON;

    public void setItem(Item item) {
        this.item = item;
    }
}
