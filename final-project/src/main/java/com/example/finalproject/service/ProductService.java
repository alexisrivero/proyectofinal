package com.example.finalproject.service;

import com.example.finalproject.web.DTO.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProduct(long id);
}
