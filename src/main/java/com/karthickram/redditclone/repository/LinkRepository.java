package com.karthickram.redditclone.repository;

import com.karthickram.redditclone.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Link findByTitle(String title);
}
