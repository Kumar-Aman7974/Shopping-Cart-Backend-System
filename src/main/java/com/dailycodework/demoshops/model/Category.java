package com.dailycodework.demoshops.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;



    // One category can have multiple products means 1:M relationship
    @OneToMany(mappedBy = "category")
   // @JsonBackReference(value = "category-product")
    @JsonIgnore
    private List<Product> products;


//    public Category(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    public Category(String name) {
        this.name = name;
    }
}
