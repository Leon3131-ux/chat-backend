package com.danilojakob.application.converter;

import com.danilojakob.application.domain.Room;
import com.danilojakob.application.domain.User;
import com.danilojakob.application.dtos.RoomDto;
import com.danilojakob.application.dtos.SaveRoomDto;
import com.danilojakob.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomConverter {

    private final UserRepository userRepository;

    public RoomDto toDto(Room room){
        return new RoomDto(room.getId(), room.getName(), room.getSubscribedUsers().stream().map(User::getId).collect(Collectors.toList()));
    }

    public Room toEntity(SaveRoomDto saveRoomDto){
        List<User> subscribedUsers = new ArrayList<>();
        if(saveRoomDto.getSubscribedUserIds() != null){
            saveRoomDto.getSubscribedUserIds().forEach(subscribedUserId -> {
                Optional<User> optionalUser = userRepository.findById(subscribedUserId);
                optionalUser.ifPresent(subscribedUsers::add);
            });
        }
        return new Room(saveRoomDto.getName(), subscribedUsers);
    }

}
