package com.Nahudev.products_service_apiRest.service;

import com.Nahudev.products_service_apiRest.dto.ProductDTO;
import com.Nahudev.products_service_apiRest.dto.ProductsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws Exception;

    public ProductDTO editProduct(Long id, ProductDTO productDTO, MultipartFile image) throws Exception;

    public void reduceStock(Long id, int amount) throws Exception;

    public void deleteProduct(Long id);

    public ProductDTO getProductById(Long id);

    public ProductsDTO getAllProducts(int numPage, int pageSize, String orderBy, String sortDir);

    public ProductsDTO getAllProductsByCategory(String category, int numPage, int pageSize, String orderBy, String sortDir);

}
