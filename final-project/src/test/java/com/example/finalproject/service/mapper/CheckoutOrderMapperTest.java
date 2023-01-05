package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.OrderProduct;
import com.example.finalproject.persistence.model.Orders;
import com.example.finalproject.web.DTO.PaymentMethodDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CheckoutOrderMapperTest {
    private final CheckoutOrderMapper mapper = Mappers.getMapper(CheckoutOrderMapper.class);

    @Test
    @DisplayName("Mapping checkout to order")
    void checkoutToOrder_Empty_Null()
    {
        Orders order = mapper.checkoutToOrder(null);
        assertThat(order,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping checkout product to order product")
    void checkoutProductToOrderProduct_Empty_Null()
    {
        OrderProduct orderProduct = mapper.checkoutProductToOrderProduct(null);
        assertThat(orderProduct,is(equalTo(null)));
    }
}