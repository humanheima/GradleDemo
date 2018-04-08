package com.hm.gradledemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dumingwei on 2018/4/8 0008.
 */
public class Person {

    private int age;
    private String name;
    private List<Person> friends;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getFriends() {
        if (friends == null) {
            return new ArrayList<>();
        }
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }
}
