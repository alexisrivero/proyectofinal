package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.OrderProduct;
import com.example.finalproject.persistence.model.Orders;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.web.DTO.OrderDTO;
import com.example.finalproject.web.DTO.OrderProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "firstName", source = "order.user.firstName")
    @Mapping(target = "lastName", source = "order.user.lastName")
    OrderDTO orderToOrderDTO (Orders order);

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "orderProduct.quantity")
    OrderProductDTO orderProductAndProductToOrderProductDTO (OrderProduct orderProduct, Product product);
}
