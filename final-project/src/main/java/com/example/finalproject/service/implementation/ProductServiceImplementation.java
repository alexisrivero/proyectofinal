package com.example.finalproject.service.implementation;

import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.persistence.model.Product;
import com.example.finalproject.persistence.repository.ProductRepository;
import com.example.finalproject.service.ProductService;
import com.example.finalproject.service.mapper.ProductsMapper;
import com.example.finalproject.web.DTO.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    /**
     * This method will return all existing products on the store
     * @return List of productDTO containtin the information of each product in the store
     * @throws ResourceNotFoundException when there are no products available in the store
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty())
        {
            throw new ResourceNotFoundException("There are no products available");
        }

        return ProductsMapper.INSTANCE.productsToProductDTOS(products);
    }

    /**
     * This method will return an existing product of the store based on its id
     * @param id The product identifier used to find the product on the repository
     * @return List of productDTO containtin the information of each product in the store
     * @throws ResourceNotFoundException when there are no products available in the store
     */
    @Override
    public ProductDTO getProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
        {
            throw new ResourceNotFoundException("There is no product with this id");
        }
        return ProductsMapper.INSTANCE.productToProductDTO(product.get());
    }
}
