package com.dailycodework.demoshops.service.category;

import com.dailycodework.demoshops.exceptions.AlreadyExistsException;

import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Category;
import com.dailycodework.demoshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService  implements ICategoryService
{

   @Autowired
   private final CategoryRepository categoryRepository;

   public CategoryService(CategoryRepository categoryRepository)
   {
       this.categoryRepository = categoryRepository;
   }

    @Override
    public Category getCategoryById(Long id) {
//        return categoryRepository.findById(id)
//                .orElseThrow( () -> new ResourceNotFoundException("Category not found"));

        // instead of we are writing this - we can also write this way

        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isEmpty()) {

            throw  new ResourceNotFoundException("Category not found");

        }

        return optional.get();

    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {

        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() ->
                        new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());

                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow( () -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {

       categoryRepository.findById(id)
               .ifPresentOrElse(categoryRepository::delete, () -> {
                   throw  new ResourceNotFoundException("Category not found!");
               });
    }
}
