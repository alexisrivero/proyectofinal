package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.CheckoutProduct;
import com.example.finalproject.persistence.model.OrderProduct;
import com.example.finalproject.persistence.model.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CheckoutOrderMapper {

    CheckoutOrderMapper INSTANCE = Mappers.getMapper(CheckoutOrderMapper.class);

    Orders checkoutToOrder(Checkout checkout);

    OrderProduct checkoutProductToOrderProduct (CheckoutProduct checkoutProduct);
}
