package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.User;
import com.example.finalproject.web.DTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO (User user);

}
