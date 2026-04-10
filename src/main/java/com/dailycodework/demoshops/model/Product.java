package com.dailycodework.demoshops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;


    // mapping these are two entities
    // multiple products that belongs to one Category M:1 relationship
    @ManyToOne(cascade = CascadeType.ALL) // Why we are used here?
    @JoinColumn(name = "category_id")
    @JsonManagedReference(value = "category-product")
    private Category category;


    // One product can have multiple images. means 1:M relationship
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // which means once a product is being to be deleted
    //  all the images that are associated with that products are going to be deleted along;
    // because we are used orphanRemoval = true;
    @JsonManagedReference(value = "product-image")
    private List<Image> images;




    // instead of using the @AllArgumentConstructor we are writing manually
 public Product(String name, String brand, BigDecimal price,
                int inventory, String description, Category category) {

  this.name = name;
  this.brand = brand;
  this.price = price;
  this.inventory = inventory;
  this.description = description;
  this.category = category;   // VERY IMPORTANT
 }
}
