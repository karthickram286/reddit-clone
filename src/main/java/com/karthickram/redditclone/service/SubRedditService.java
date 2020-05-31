package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.SubRedditDto;
import com.karthickram.redditclone.models.SubReddit;
import com.karthickram.redditclone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .name(subRedditDto.getSubRedditName())
                .description(subRedditDto.getDescription())
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
        return SubRedditDto.builder().subRedditName(subReddit.getName())
                .id(subReddit.getId())
                .description(subReddit.getDescription())
                .numberOfPosts(subReddit.getPosts().size())
                .build();
    }
}
