package com.springframework.sfgrecipes.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springframework.sfgrecipes.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
