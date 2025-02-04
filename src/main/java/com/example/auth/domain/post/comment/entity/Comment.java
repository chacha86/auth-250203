package com.example.auth.domain.post.comment.entity;

import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.post.post.entity.Post;
import com.example.auth.global.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Comment extends BaseTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String content;

    public void modify(String content) {
        this.content = content;
    }

}
