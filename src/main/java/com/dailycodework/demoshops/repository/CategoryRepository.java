package com.dailycodework.demoshops.repository;

import com.dailycodework.demoshops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByName(String name);

    boolean existsByName(String name);
}
