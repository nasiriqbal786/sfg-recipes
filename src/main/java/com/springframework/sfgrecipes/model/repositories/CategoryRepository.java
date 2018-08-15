package com.springframework.sfgrecipes.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springframework.sfgrecipes.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
