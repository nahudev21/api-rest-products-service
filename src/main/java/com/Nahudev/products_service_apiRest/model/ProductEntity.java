package com.Nahudev.products_service_apiRest.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "productos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "imagen")
    private String image;

    @Column(name = "precio")
    private Double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "categoria")
    private String category;

    public ProductEntity() {
    }

    public ProductEntity(Long id, String name, String description, String image, Double price,
                         int stock, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                '}';
    }
}
