package com.danilojakob.application.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SaveRoomDto {

    private String name;
    private List<Long> subscribedUserIds;

}
