package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Orders;
import com.example.finalproject.web.DTO.CheckoutDTO;
import com.example.finalproject.web.DTO.CheckoutProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CheckoutMapperTest {

    private final CheckoutMapper mapper = Mappers.getMapper(CheckoutMapper.class);

    @Test
    @DisplayName("Mapping checkout to CheckoutDTO")
    void checkoutToCheckoutDTO_Empty_Null()
    {
        CheckoutDTO checkoutDTO = mapper.checkoutToCheckoutDTO(null);
        assertThat(checkoutDTO,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping checkout product and product to CheckoutProductDTO")
    void checkoutProductAndProductToProductCheckoutDTO_Empty_Null()
    {
        CheckoutProductDTO checkoutProductDTO = mapper.checkoutProductAndProductToProductCheckoutDTO(null,null);
        assertThat(checkoutProductDTO,is(equalTo(null)));
    }
}