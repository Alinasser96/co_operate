package com.co_operate.aly.co_operate;

import java.io.Serializable;

/**
 * Created by aly on 4/13/18.
 */

public class Post   implements Serializable {
    private String post,id,date,name;
    private int likes;
    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post(String post, int likes, String id,String date,String name) {
        this.post = post;
        this.likes = likes;
        this.id=id;
        this.date=date;
        this.name=name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
