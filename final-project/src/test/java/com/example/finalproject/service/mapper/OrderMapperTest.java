package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.OrderProduct;
import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.web.DTO.OrderDTO;
import com.example.finalproject.web.DTO.OrderProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OrderMapperTest {
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Test
    @DisplayName("Mapping order to OrderDTO ")
    void orderToOrderDTO_Empty_Null()
    {
        OrderDTO orderDTO = mapper.orderToOrderDTO(null);
        assertThat(orderDTO,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping orderProduct and product to OrderProductDto ")
    void orderProductAndProductToOrderProductDTO_Empty_Null()
    {
        OrderProductDTO orderProductDTO = mapper.orderProductAndProductToOrderProductDTO(null,null);
        assertThat(orderProductDTO,is(equalTo(null)));
    }
}