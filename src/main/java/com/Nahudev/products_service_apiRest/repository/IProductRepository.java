package com.Nahudev.products_service_apiRest.repository;

import com.Nahudev.products_service_apiRest.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
}
