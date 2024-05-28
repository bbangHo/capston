package com.example.capstone.post;

import com.example.capstone.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    private String originalFileName;

    @NotNull
    private String imageUrl;

    @Column(unique = true)
    private UUID uuid;

    public void setItem(Post post) {
        this.post = post;
    }

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
