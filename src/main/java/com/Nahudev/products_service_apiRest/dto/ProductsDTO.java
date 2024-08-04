package com.Nahudev.products_service_apiRest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {

    List<ProductDTO> products;

    private int pageNumber;

    private int pageSize;

    private Long totalItems;

    private int totalPages;

    private boolean last;

}
