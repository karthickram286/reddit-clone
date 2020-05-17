package com.karthickram.redditclone.models;

import com.karthickram.redditclone.audit.Auditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Comment extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String body;

    @ManyToOne
    @NonNull
    private Link link;
}
