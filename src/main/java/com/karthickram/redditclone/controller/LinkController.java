package com.karthickram.redditclone.controller;

import com.karthickram.redditclone.models.Link;
import com.karthickram.redditclone.repository.LinkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {

    private LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/getAllLinks")
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @PostMapping("/create")
    public Link create(@ModelAttribute Link link) {
        return linkRepository.save(link);
    }

    @GetMapping("/{id}")
    public Optional<Link> getLink(@PathVariable Long id) {
        return linkRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Link updateLink(@ModelAttribute Link link) {
        return linkRepository.save(link);
    }

    @DeleteMapping("/{id}")
    public void deleteLink(@PathVariable Long id) {
        linkRepository.deleteById(id);
    }
}
