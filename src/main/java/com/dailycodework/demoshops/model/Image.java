package com.dailycodework.demoshops.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private  String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;




    // Multiple images has 1 products means M:1 relationship
    @ManyToOne
    // Set the name of the column using Join
//    @JsonIgnore
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "product-image")
    private Product product;




}
