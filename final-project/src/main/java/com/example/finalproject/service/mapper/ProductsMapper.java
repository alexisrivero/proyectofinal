package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.web.DTO.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ProductsMapper {

    ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    ProductDTO productToProductDTO (Product product);

    List<ProductDTO> productsToProductDTOS (List<Product> products);
}
