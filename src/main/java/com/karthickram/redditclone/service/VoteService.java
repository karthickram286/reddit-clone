package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.VoteDto;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.Post;
import com.karthickram.redditclone.models.Vote;
import com.karthickram.redditclone.models.VoteType;
import com.karthickram.redditclone.repository.PostRepository;
import com.karthickram.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                        .orElseThrow(() -> new RedditCloneException("Post not found for id: " + voteDto.getPostId()));

        if (voteDto.getVoteType().equals(VoteType.UP_VOTE)) {
            post.setVoteCount(1);
        } else{
            post.setVoteCount(-1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
