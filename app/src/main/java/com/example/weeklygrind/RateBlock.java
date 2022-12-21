package com.example.weeklygrind;

public class RateBlock {

    private String Comment, Nickname, Rating;

    public RateBlock() {
    }

    public RateBlock(String comment, String nickname, String rating) {
        Comment = comment;
        Nickname = nickname;
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
