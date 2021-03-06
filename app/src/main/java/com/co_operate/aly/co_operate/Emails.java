package com.co_operate.aly.co_operate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aly on 3/14/18.
 */

public class Emails implements Serializable {
    String email_name,email_id,name,group;
    private ArrayList<Post> posts ;
    ArrayList<String> friends;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public Emails(String email_name, String email_id, String name, String group, ArrayList posts, ArrayList friends) {

        this.email_name = email_name;
        this.email_id = email_id;
        this.name = name;
        this.group = group;
        this.posts=posts;
        this.friends=friends;

    }

    public Emails() {
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public String getEmail_name() {
        return email_name;
    }

    public void setEmail_name(String email_name) {
        this.email_name = email_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
