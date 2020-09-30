package com.danilojakob.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Room extends AbstractEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<User> subscribedUsers;

    public void subscribe(User user){
        this.subscribedUsers.add(user);
    }

    public void unsubscribe(User user){
        this.subscribedUsers.remove(user);
    }

}
