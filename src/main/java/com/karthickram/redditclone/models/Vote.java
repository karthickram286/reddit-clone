package com.karthickram.redditclone.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private int vote;
}
