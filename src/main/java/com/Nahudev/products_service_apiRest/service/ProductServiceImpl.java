package com.Nahudev.products_service_apiRest.service;

import com.Nahudev.products_service_apiRest.dto.ProductDTO;
import com.Nahudev.products_service_apiRest.dto.ProductsDTO;
import com.Nahudev.products_service_apiRest.exceptions.ResourceNotFoundException;
import com.Nahudev.products_service_apiRest.model.ProductEntity;
import com.Nahudev.products_service_apiRest.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws Exception {

        ProductEntity productEntity = mapOutProductEntity(productDTO);
        String nameImage = uploadFileService.handleFileUpload(image);
        productEntity.setImage(nameImage);

        ProductEntity newProduct = productRepository.save(productEntity);

        return mapOutProductDTO(newProduct);
    }

    @Override
    public ProductDTO editProduct(Long id, ProductDTO productDTO, MultipartFile image) throws Exception {

        ProductEntity productFound = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publicacion", "id", id));

        if (image != null) {
            String nameImage = uploadFileService.handleFileUpload(image);
            productFound.setImage(nameImage);
        } else {
            productFound.setName(productDTO.getName());
            productFound.setDescription(productDTO.getDescription());
            productFound.setPrice(productDTO.getPrice());
            productFound.setStock(productDTO.getStock());
            productFound.setCategory(productDTO.getCategory());

        }

        ProductEntity productEdited = productRepository.save(productFound);

        return mapOutProductDTO(productEdited);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity productFound = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publicacion", "id", id));
        productRepository.delete(productFound);
    }

    @Override
    public ProductDTO getProductById(Long id) {

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publicacion", "id", id));
        return mapOutProductDTO(productEntity);
    }

    @Override
    public ProductsDTO getAllProducts(int numPage, int pageSize, String orderBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(orderBy).ascending():Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(numPage, pageSize, sort);
        Page<ProductEntity> products = productRepository.findAll(pageable);

        List<ProductEntity> productEntities = products.getContent();
        List<ProductDTO> listProducts = productEntities.stream().map(this::mapOutProductDTO).collect(Collectors.toList());

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProducts(listProducts);
        productsDTO.setPageNumber(products.getNumber());
        productsDTO.setPageSize(products.getSize());
        productsDTO.setTotalItems(products.getTotalElements());
        productsDTO.setTotalPages(products.getTotalPages());
        productsDTO.setLast(products.isLast());

        return productsDTO;
    }

    @Override
    public ProductsDTO getAllProductsByCategory(String category, int numPage, int pageSize, String orderBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(orderBy).ascending():Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(numPage, pageSize, sort);
        Page<ProductEntity> products = productRepository.getAllProductsByCategory(category, pageable);

        List<ProductEntity> listProductsByCategory = products.getContent();
        List<ProductDTO> listProducts = listProductsByCategory.stream().map(this::mapOutProductDTO).collect(Collectors.toList());

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProducts(listProducts);
        productsDTO.setPageNumber(products.getNumber());
        productsDTO.setPageSize(products.getSize());
        productsDTO.setTotalItems(products.getTotalElements());
        productsDTO.setTotalPages(products.getTotalPages());
        productsDTO.setLast(products.isLast());

        return productsDTO;
    }

    private ProductDTO mapOutProductDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    private ProductEntity mapOutProductEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, ProductEntity.class);
    }

}
