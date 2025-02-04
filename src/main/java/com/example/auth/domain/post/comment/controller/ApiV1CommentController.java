package com.example.auth.domain.post.comment.controller;

import com.example.auth.domain.member.member.entity.Member;
import com.example.auth.domain.post.comment.dto.CommentDto;
import com.example.auth.domain.post.comment.entity.Comment;
import com.example.auth.domain.post.post.entity.Post;
import com.example.auth.domain.post.post.service.PostService;
import com.example.auth.global.Rq;
import com.example.auth.global.dto.RsData;
import com.example.auth.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class ApiV1CommentController {

    private final PostService postService;
    private final Rq rq;

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

    @GetMapping("{id}")
    public CommentDto getItem(@PathVariable long postId, @PathVariable long id) {

        Post post = postService.getItem(postId).orElseThrow(
                () -> new ServiceException("404-1", "존재하지 않는 게시글입니다.")
        );

        Comment comment = post.getCommentById(id);

        return new CommentDto(comment);
    }


    record WriteReqBody(String content) {
    }

    @PostMapping
    public RsData<Void> write(@PathVariable long postId, @RequestBody WriteReqBody reqBody) {
        Member actor = rq.getAuthenticatedActor();

        Post post = postService.getItem(postId).orElseThrow(
                () -> new ServiceException("404-1", "존재하지 않는 게시글입니다.")
        );

        Comment comment = post.addComment(actor, reqBody.content());

        return new RsData<>(
                "201-1",
                "%d번 댓글 작성이 완료되었습니다.".formatted(comment.getId())
        );

    }


}
