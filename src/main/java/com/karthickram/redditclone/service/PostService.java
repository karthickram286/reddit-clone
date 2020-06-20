package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.PostsDto;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.Post;
import com.karthickram.redditclone.models.SubReddit;
import com.karthickram.redditclone.models.User;
import com.karthickram.redditclone.repository.PostRepository;
import com.karthickram.redditclone.repository.SubRedditRepository;
import com.karthickram.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final UserRepository userRepository;
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

    @Transactional(readOnly = true)
    public PostsDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RedditCloneException("Post not found for the id: " + id));
        return mapToPostsDto(post);
    }

    private PostsDto mapToPostsDto(Post post) {
        return PostsDto.builder()
                .postId(post.getPostId())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())
                .user(authService.getCurrentUser().getUserName())
                .subredditName(post.getSubReddit().getName())
                .voteCount(post.getVoteCount())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToPostsDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsDto> getPostsBySubredditId(Long subredditId) {
        SubReddit subReddit = subRedditRepository.findById(subredditId)
                .orElseThrow(() -> new RedditCloneException("Subreddit not found for the id: " + subredditId));

        List<Post> postsBySubreddit = postRepository.findAllBySubReddit(subReddit);
        return postsBySubreddit.stream()
                .map(this::mapToPostsDto)
                .collect(Collectors.toList());
    }

    public List<PostsDto> getPostsByEmailId(String emailId) {
        User user = userRepository.findByEmail(emailId)
                .orElseThrow(() -> new RedditCloneException("User not found for the email: " + emailId));

        List<Post> postsByEmailId = postRepository.findByUser(user);
        return postsByEmailId.stream()
                .map(this::mapToPostsDto)
                .collect(Collectors.toList());
    }
}
