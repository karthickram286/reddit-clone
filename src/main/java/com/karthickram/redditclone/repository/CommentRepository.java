package com.karthickram.redditclone.repository;

import com.karthickram.redditclone.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
