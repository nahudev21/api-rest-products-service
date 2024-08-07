package com.Nahudev.products_service_apiRest.controller;

import com.Nahudev.products_service_apiRest.dto.ProductDTO;
import com.Nahudev.products_service_apiRest.dto.ProductsDTO;
import com.Nahudev.products_service_apiRest.service.IProductService;
import com.Nahudev.products_service_apiRest.service.UploadFileServiceImpl;
import com.Nahudev.products_service_apiRest.utils.AppConstants;
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

    @GetMapping()
    @ResponseBody
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "numPage", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int numPage,
                                            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String orderBy,
                                            @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DEFAULT_ADDRESS, required = false) String sortDir) {

        ProductsDTO listProducts = productService.getAllProducts(numPage, pageSize, orderBy, sortDir);

        if (listProducts != null) {
            return ResponseEntity.ok(listProducts);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getByCategory")
    @ResponseBody
    public ResponseEntity<?> getAllProductsByCategory(@RequestParam(name = "category") String category,
                                                      @RequestParam(name = "numPage", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int numPage,
                                                      @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                      @RequestParam(name = "orderBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String orderBy,
                                                      @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DEFAULT_ADDRESS, required = false) String sortDir) {

        ProductsDTO lisProducts = productService.getAllProductsByCategory(category, numPage, pageSize, orderBy, sortDir);

        if (lisProducts != null) {
            return ResponseEntity.ok(lisProducts);
        }

        return ResponseEntity.badRequest().build();
    }

}
