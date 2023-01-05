package com.example.finalproject.service.implementation;

import com.example.finalproject.ObjectCreator;
import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.persistence.repository.ProductRepository;
import com.example.finalproject.web.DTO.ProductDTO;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImplementation productServiceImplementation;

    private ObjectCreator objectCreator;

    @BeforeEach
    private void setUp ()
    {
        productServiceImplementation = new ProductServiceImplementation(productRepository);
        objectCreator = new ObjectCreator();
    }

    @Nested
    @DisplayName("getProduct")
    class getProduct {
        @Test
        @DisplayName("getProduct When valid id return valid ProductDTO")
        void getProduct_ValidID_GetProductDTO()
        {
            Product product = objectCreator.createProduct();
            Optional<Product> optionalProduct = Optional.of(product);
            ProductDTO productDTO = objectCreator.createProductDTO();

            when(productRepository.findById(anyLong())).thenReturn(optionalProduct);

            ProductDTO getProductDTO = productServiceImplementation.getProduct(1L);

            assertThat(getProductDTO,samePropertyValuesAs(productDTO));

            verify(productRepository).findById(anyLong());
        }

        @Test
        @DisplayName("getProduct When invalid id throw exception")
        void getProduct_InValidID_ThrowException()
        {
            Optional<Product> optionalProduct = Optional.empty();
            when(productRepository.findById(anyLong())).thenReturn(optionalProduct);

            assertThrows(ResourceNotFoundException.class,() ->
                    productServiceImplementation.getProduct(1L));

            verify(productRepository).findById(anyLong());
        }
    }

    @Nested
    @DisplayName("getAllProducts")
    class getAllProducts {
        @Test
        @DisplayName("getAllProducts When available Products return List ProductDto")
        void getProduct_AvailableProducts_GetListProductDTO()
        {
            List<Product> productList = new ArrayList<>();
            Product product = objectCreator.createProduct();
            productList.add(product);

            List<ProductDTO> productDTOList = new ArrayList<>();
            ProductDTO productDTO = objectCreator.createProductDTO();
            productDTOList.add(productDTO);

            when(productRepository.findAll()).thenReturn(productList);

            List<ProductDTO> getProductDTO = productServiceImplementation.getAllProducts();

            assertThat(getProductDTO.size(),is(productDTOList.size()));

            verify(productRepository).findAll();
        }

        @Test
        @DisplayName("getAllProducts When no available Products Throw Exception")
        void getProduct_NoAvailableProducts_ThrowException()
        {
            List<Product> productList = new ArrayList<>();
            when(productRepository.findAll()).thenReturn(productList);

            assertThrows(ResourceNotFoundException.class,() ->
                    productServiceImplementation.getAllProducts());

            verify(productRepository).findAll();
        }
    }



}