package com.example.auth.domain.post.post.entity;

import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.post.comment.entity.Comment;
import com.example.auth.global.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post extends BaseTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments;

}
