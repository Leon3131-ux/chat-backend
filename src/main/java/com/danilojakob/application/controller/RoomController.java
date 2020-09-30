package com.danilojakob.application.controller;

import com.danilojakob.application.converter.RoomConverter;
import com.danilojakob.application.domain.Room;
import com.danilojakob.application.domain.User;
import com.danilojakob.application.dtos.RoomDto;
import com.danilojakob.application.dtos.SaveRoomDto;
import com.danilojakob.application.repository.UserRepository;
import com.danilojakob.application.service.RoomService;
import com.danilojakob.application.validator.RoomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final UserRepository userRepository;
    private final RoomService roomService;
    private final RoomConverter roomConverter;
    private final RoomValidator roomValidator;

    @InitBinder("saveRoomDto")
    public void initChangeCommuneBinder(WebDataBinder binder) {
        binder.setValidator(roomValidator);
    }
    
    @RequestMapping(value = "/api/rooms/subscribe/{roomId}", method = RequestMethod.POST)
    public ResponseEntity subscribeToRoom(@PathVariable(value = "roomId") Room room, Principal principal){
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(!room.getSubscribedUsers().contains(user)){
                room.subscribe(user);
                roomService.save(room);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/rooms/unsubscribe/{roomId}", method = RequestMethod.POST)
    public ResponseEntity unsubscribeFromRoom(@PathVariable(value = "roomId") Room room, Principal principal){
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            room.unsubscribe(user);
            roomService.save(room);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/rooms")
    public List<RoomDto> getRooms(){
        return roomService.getRooms().stream().map(roomConverter::toDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/rooms/create", method = RequestMethod.POST)
    public ResponseEntity createRoom(@Validated @RequestBody SaveRoomDto saveRoomDto){
        Room room = roomConverter.toEntity(saveRoomDto);
        return new ResponseEntity<>(roomConverter.toDto(roomService.save(room)), HttpStatus.CREATED);
    }
}
