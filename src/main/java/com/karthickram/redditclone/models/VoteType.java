package com.karthickram.redditclone.models;

public enum VoteType {
    UP_VOTE(1),
    DOWN_VOTE(-1);

    private int direction;

    VoteType(int direction) {
    }
}
