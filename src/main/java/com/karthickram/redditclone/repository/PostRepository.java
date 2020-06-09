package com.karthickram.redditclone.repository;

import com.karthickram.redditclone.models.Post;
import com.karthickram.redditclone.models.SubReddit;
import com.karthickram.redditclone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubReddit(SubReddit subReddit);

    List<Post> findByUser(User user);
}
