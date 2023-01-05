package com.example.finalproject.service.mapper;

import com.example.finalproject.web.DTO.ProductDTO;
import com.example.finalproject.web.DTO.UserAddressDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ProductsMapperTest {

    private final ProductsMapper mapper = Mappers.getMapper(ProductsMapper.class);

    @Test
    @DisplayName("Mapping product to ProductDTO")
    void addressToCheckoutUserAddressDTO_Empty_Null()
    {
        ProductDTO productDTO = mapper.productToProductDTO(null);
        assertThat(productDTO,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping products to ProducDTOList")
    void productsToProductDTOS_EmptyList_NullList()
    {
        List<ProductDTO> productDTO = mapper.productsToProductDTOS(null);
        assertThat(productDTO,is(equalTo(null)));
    }
}