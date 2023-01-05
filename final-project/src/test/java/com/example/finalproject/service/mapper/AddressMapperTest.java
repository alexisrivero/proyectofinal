package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Address;
import com.example.finalproject.web.DTO.UserAddressDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class AddressMapperTest {

    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    @DisplayName("Mapping address to checkoutUserAddressDTO")
    void addressToCheckoutUserAddressDTO_EmptyList_NullList()
    {
        List<UserAddressDTO> list = mapper.addressToCheckoutUserAddressDTO(null);
        assertThat(list,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping createAddressDTO to Address")
    void createAddressDTOToAddress_Empty_Null()
    {
        Address address = mapper.createAddressDTOToAddress(null);
        assertThat(address,is(equalTo(null)));
    }
}