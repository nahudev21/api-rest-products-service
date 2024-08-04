package com.Nahudev.products_service_apiRest.service;

import com.Nahudev.products_service_apiRest.dto.ProductDTO;
import com.Nahudev.products_service_apiRest.dto.ProductsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws Exception;

    public ProductDTO editProduct(Long id, ProductDTO productDTO);

    public void deleteProduct(Long id);

    public ProductDTO getProductById(Long id);

    public ProductsDTO getAllProducts();

}
