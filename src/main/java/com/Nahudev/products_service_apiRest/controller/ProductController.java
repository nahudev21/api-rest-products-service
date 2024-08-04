package com.Nahudev.products_service_apiRest.controller;

import com.Nahudev.products_service_apiRest.dto.ProductDTO;
import com.Nahudev.products_service_apiRest.service.IProductService;
import com.Nahudev.products_service_apiRest.service.UploadFileServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestParam(value = "file", required = false) MultipartFile file,
                                                @RequestParam(value = "data") String productString) throws Exception {

        ProductDTO productDTO = new ObjectMapper().readValue(productString, ProductDTO.class);

        return new ResponseEntity<>(productService.createProduct(productDTO, file), HttpStatus.CREATED);
    }

    @PutMapping("/edited/{id}")
    public ResponseEntity<ProductDTO> editProduct(@PathVariable(name = "id") Long id,
                                                  @RequestParam(value ="data", required = false) String productString,
                                                  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        ProductDTO productDTO = new ObjectMapper().readValue(productString, ProductDTO.class);

        return new ResponseEntity<>(productService.editProduct(id, productDTO, file), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("El producto fue eliminado correctamente", HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
}
