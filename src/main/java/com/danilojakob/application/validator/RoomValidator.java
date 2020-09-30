package com.danilojakob.application.validator;


import com.danilojakob.application.dtos.SaveRoomDto;
import com.danilojakob.application.dtos.SignUpDto;
import com.danilojakob.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RoomValidator implements Validator {

	private final UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return SaveRoomDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SaveRoomDto saveRoomDto = (SaveRoomDto) target;

		if(saveRoomDto.getName() == null || saveRoomDto.getName().isBlank()){
			errors.reject("roomName", "validate.roomName.isBlank");
		}
	}
}
