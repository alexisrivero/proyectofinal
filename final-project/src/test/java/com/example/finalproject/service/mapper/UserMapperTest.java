package com.example.finalproject.service.mapper;

import com.example.finalproject.web.DTO.CheckoutDTO;
import com.example.finalproject.web.DTO.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("Mapping user to UserDTO")
    void userToUserDTO_Empty_Null()
    {
        UserDTO userDTO = mapper.userToUserDTO(null);
        assertThat(userDTO,is(equalTo(null)));
    }
}