package com.dailycodework.demoshops.dto;

import com.dailycodework.demoshops.model.Category;
import com.dailycodework.demoshops.model.Image;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}

// 3 :17
