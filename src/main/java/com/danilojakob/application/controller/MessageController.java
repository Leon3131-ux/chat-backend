package com.danilojakob.application.controller;

import com.danilojakob.application.domain.User;
import com.danilojakob.application.dtos.MessageDto;
import com.danilojakob.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final UserRepository userRepository;
    private final SimpMessageSendingOperations simpMessagingTemplate;

    @MessageMapping(value = "/send/{roomId}")
    public void send(@DestinationVariable("roomId") String roomId, String message, Principal principal){
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            MessageDto messageDto = new MessageDto(message, user.getUsername());
            simpMessagingTemplate.convertAndSend("/message/" + roomId, messageDto);
        }

    }

}
