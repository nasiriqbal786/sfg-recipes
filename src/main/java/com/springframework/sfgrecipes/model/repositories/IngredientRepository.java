package com.springframework.sfgrecipes.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springframework.sfgrecipes.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
