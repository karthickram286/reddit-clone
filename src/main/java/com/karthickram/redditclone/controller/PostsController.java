package com.karthickram.redditclone.controller;

import com.karthickram.redditclone.dto.PostsDto;
import com.karthickram.redditclone.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostsController {

    private final PostService postService;

    @PostMapping
    @ApiOperation(value = "Creates a new post",
            response = PostsDto.class)
    public ResponseEntity createPost(@RequestBody PostsDto postsDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(postsDto));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PostsDto> getPost(@PathVariable Long id) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(postService.getPost(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PostsDto>> getAllPosts() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(postService.getAllPosts());
//    }
//
//    @GetMapping("/subreddit/{subredditId}")
//    public ResponseEntity<List<PostsDto>> getPostsBySubreddit(@PathVariable Long subredditId) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(postService.getPostsBySubredditId(subredditId));
//    }
//
//    @GetMapping("/user/{emailId}")
//    public ResponseEntity<List<PostsDto>> getPostsByEmailId(@PathVariable String emailId) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(postService.getPostsByEmailId(emailId));
//    }
}
