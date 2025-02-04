package com.example.auth.domain.post.comment.controller;

import com.example.auth.domain.post.comment.dto.CommentDto;
import com.example.auth.domain.post.comment.entity.Comment;
import com.example.auth.domain.post.post.entity.Post;
import com.example.auth.domain.post.post.service.PostService;
import com.example.auth.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class ApiV1CommentController {

    private final PostService postService;

    @GetMapping
    public List<CommentDto> getItems(@PathVariable long postId) {

        Post post = postService.getItem(postId).orElseThrow(
                () -> new ServiceException("404-1", "존재하지 않는 게시글입니다.")
        );

        return post.getComments()
                .stream()
                .map(CommentDto::new)
                .toList();
    }


}
