package com.example.auth.domain.post.comment.dto;

import com.example.auth.domain.post.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {

    private String content;
    private long postId;
    private long authorId;
    private String authorName;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.authorId = comment.getAuthor().getId();
        this.authorName = comment.getAuthor().getNickname();
        this.createdTime = comment.getCreatedDate();
        this.modifiedTime = comment.getModifiedDate();
    }

}
