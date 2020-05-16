package com.karthickram.redditclone.models;

import com.karthickram.redditclone.audit.Auditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Link extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String url;

    @OneToMany(mappedBy = "Link")
    private List<Comment> comments = new ArrayList<>();
}
