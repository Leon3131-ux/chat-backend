package com.danilojakob.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomDto {

    private Long roomId;
    private String name;
    private List<Long> subscribedUserIds;

}
