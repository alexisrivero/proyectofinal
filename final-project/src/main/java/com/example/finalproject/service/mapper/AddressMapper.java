package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Address;
import com.example.finalproject.web.DTO.UserAddressDTO;
import com.example.finalproject.web.DTO.CreateAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    List<UserAddressDTO> addressToCheckoutUserAddressDTO(List<Address> address);

    Address createAddressDTOToAddress(CreateAddressDTO createAddressDTO);
}
