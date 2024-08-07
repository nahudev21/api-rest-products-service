package com.Nahudev.products_service_apiRest.repository;

import com.Nahudev.products_service_apiRest.dto.ProductsDTO;
import com.Nahudev.products_service_apiRest.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.category LIKE %:category%")
    public Page<ProductEntity> getAllProductsByCategory(@Param("category") String category, Pageable pageable);

}
