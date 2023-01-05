package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Checkout;
import com.example.finalproject.persistence.model.CheckoutProduct;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.web.DTO.CheckoutDTO;
import com.example.finalproject.web.DTO.CheckoutProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CheckoutMapper {

    CheckoutMapper INSTANCE = Mappers.getMapper(CheckoutMapper.class);

    @Mapping(target = "userName", source = "checkout.user.userName")
    @Mapping(target = "firstName", source = "checkout.user.firstName")
    @Mapping(target = "lastName", source = "checkout.user.lastName")
    CheckoutDTO checkoutToCheckoutDTO(Checkout checkout);

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "checkoutProduct.quantity")
    CheckoutProductDTO checkoutProductAndProductToProductCheckoutDTO (CheckoutProduct checkoutProduct, Product product);

}

