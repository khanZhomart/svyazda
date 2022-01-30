package com.svyazda.dto.converters;

import com.svyazda.dto.UserDTO;
import com.svyazda.entities.User;

public class UserConverter {

    public static User convertToEntity(UserDTO userDto) {
		User user = User.builder()
            .userId(userDto.getUserId())
            .email(userDto.getEmail())
            .username(userDto.getUsername())
            .build();

        if (userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());

        return user;
    }

    public static UserDTO convertToDTO(User userEntity) {
        return UserDTO.builder()
            .userId(userEntity.getUserId())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .build();
    }
}
