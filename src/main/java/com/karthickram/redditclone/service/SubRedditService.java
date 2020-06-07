package com.karthickram.redditclone.service;

import com.karthickram.redditclone.RedditcloneApplication;
import com.karthickram.redditclone.dto.SubRedditDto;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.SubReddit;
import com.karthickram.redditclone.repository.SubRedditRepository;
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
public class SubRedditService {

    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto) {
        SubReddit subReddit = mapSubRedditDto(subRedditDto);
        SubReddit saved = subRedditRepository.save(subReddit);

        subRedditDto.setId(saved.getId());
        return subRedditDto;
    }

    private SubReddit mapSubRedditDto(SubRedditDto subRedditDto) {
        return SubReddit.builder()
                .name(subRedditDto.getName())
                .description(subRedditDto.getDescription())
                .createdDate(Instant.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(this::mapDto)
                .collect(Collectors.toList());
    }

    private SubRedditDto mapDto(SubReddit subReddit) {
        return SubRedditDto.builder()
                .name(subReddit.getName())
                .id(subReddit.getId())
                .description(subReddit.getDescription())
                .numberOfPosts(subReddit.getPosts().size())
                .build();
    }

    @Transactional(readOnly = true)
    public SubRedditDto get(Long id) {
        Optional<SubReddit> optionalSubreddit = subRedditRepository.findById(id);
        optionalSubreddit.orElseThrow(() -> new RedditCloneException("No subreddit found for the id: " + id));

        return mapDto(optionalSubreddit.get());
    }
}
