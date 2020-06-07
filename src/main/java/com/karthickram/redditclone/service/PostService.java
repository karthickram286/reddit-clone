package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.PostsDto;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.Post;
import com.karthickram.redditclone.models.SubReddit;
import com.karthickram.redditclone.repository.PostRepository;
import com.karthickram.redditclone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final AuthService authService;

    @Transactional
    public PostsDto save(PostsDto postsDto) {
        Post savedPost = postRepository.save(mapToPost(postsDto));

        postsDto.setPostId(savedPost.getPostId());
        return postsDto;
    }

    private Post mapToPost(PostsDto postsDto) {
        return Post.builder()
                .postId(postsDto.getPostId())
                .postName(postsDto.getPostName())
                .description(postsDto.getDescription())
                .createdDate(Instant.now())
                .subReddit(getSubreddit(postsDto.getSubredditName()))
                .user(authService.getCurrentUser())
                .url(postsDto.getUrl())
                .build();
    }

    @Transactional(readOnly = true)
    private SubReddit getSubreddit(String subredditName) {
        Optional<SubReddit> optionalSubreddit = subRedditRepository.findByName(subredditName);
        optionalSubreddit.orElseThrow(() -> new RedditCloneException("Subreddit not found for the name: " + subredditName));
        return optionalSubreddit.get();
    }
}
