package com.dailycodework.demoshops.controller;


import com.dailycodework.demoshops.exceptions.AlreadyExistsException;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Category;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.category.ICategoryService;
import com.sun.security.auth.UnixNumericUserPrincipal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {

        try {
            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.ok(new ApiResponse("Found!", categories));

        }
        catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));

        }
    }


    // here we are created a new add categories controller
    @PostMapping("/add")
    public  ResponseEntity<ApiResponse> addCategories(@RequestBody Category name)
    {
        try {

            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success", theCategory));
        }
        catch (AlreadyExistsException e)
        {
            return  ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        }

    }

// here we are passed id as the path variable
    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse>  getCategoryById(@PathVariable Long id)
    {
        try {
            Category theCategory = categoryService.getCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("Found", theCategory));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }


    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse>  getCategoryByName(@PathVariable String name)
    {
        try {
            Category theCategory = categoryService.getCategoryByName(name);

            return ResponseEntity.ok(new ApiResponse("Found", theCategory));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }


    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse>  DeleteCategoryById(@PathVariable Long id)
    {
        try {
            categoryService.getCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("Delete", null));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }


    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category)
    {
        try {
            Category updateCategory = categoryService.updateCategory(category, id);

            return ResponseEntity.ok(new ApiResponse("Update success!", updateCategory));

        }
        catch (ResourceNotFoundException e) {

            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }

// Now this is end of the category implementations;



}
