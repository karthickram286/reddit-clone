package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.CommentsDto;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.Comment;
import com.karthickram.redditclone.models.Post;
import com.karthickram.redditclone.models.User;
import com.karthickram.redditclone.repository.CommentRepository;
import com.karthickram.redditclone.repository.PostRepository;
import com.karthickram.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public CommentsDto save(CommentsDto commentsDto) {
        Comment comment = mapToComment(commentsDto);
        commentRepository.save(comment);

        commentsDto.setId(comment.getCommentId());
        return commentsDto;
    }

    private Comment mapToComment(CommentsDto commentsDto) {
        return Comment.builder()
                .commentId(commentsDto.getId())
                .text(commentsDto.getText())
                .post(getPost(commentsDto.getPostId()))
                .createdDate(Instant.now())
                .user(authService.getCurrentUser())
                .build();
    }

    @Transactional(readOnly = true)
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RedditCloneException("Post not found for the id: " + postId.toString()));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = getPost(postId);
        List<Comment> comments = commentRepository.findByPost(post);

        return comments
                .stream().map(this::mapToCommentsDto)
                .collect(Collectors.toList());
    }

    private CommentsDto mapToCommentsDto(Comment comment) {
        return CommentsDto.builder()
                .id(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .userName(comment.getUser().getUserName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForUser(String emailId) {
        User user = userRepository.findByEmail(emailId)
                        .orElseThrow(() -> new RedditCloneException("User not found for emailId: " + emailId));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(this::mapToCommentsDto)
                .collect(Collectors.toList());
    }
}
