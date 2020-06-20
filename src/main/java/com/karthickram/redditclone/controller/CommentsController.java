package com.karthickram.redditclone.controller;

import com.karthickram.redditclone.dto.CommentsDto;
import com.karthickram.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentsDto commentsDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(commentsDto));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/user/{emailId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String emailId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForUser(emailId));
    }
}
