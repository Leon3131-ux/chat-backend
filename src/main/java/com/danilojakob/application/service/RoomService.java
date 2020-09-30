package com.danilojakob.application.service;

import com.danilojakob.application.domain.Room;
import com.danilojakob.application.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Optional<Room> get(int id){return  roomRepository.findById(id);}

    public List<Room> getRooms(){return roomRepository.findAll();}

    public Room save(Room room){return roomRepository.save(room);}

}
