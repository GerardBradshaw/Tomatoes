package com.gerardbradshaw.mater.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gerardbradshaw.mater.room.entities.RecipeIngredient;

@Dao
public interface RecipeIngredientDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRecipeIngredient(RecipeIngredient recipeIngredient);

  @Update
  void updateRecipeIngredient(RecipeIngredient recipeIngredient);

  @Delete
  void deleteRecipeIngredient(RecipeIngredient... recipeIngredients);


  // - - - - - - - - - - - - - - - LiveData queries - - - - - - - - - - - - - - -

  @Query("select * from recipe_ingredient_table where recipe_id = :recipeId")
  LiveData<RecipeIngredient[]> getLiveRecipeIngredients(int recipeId);


  // - - - - - - - - - - - - - - - Non-LiveData queries - - - - - - - - - - - - - - -

  @Query("select ingredient_id from recipe_ingredient_table where recipe_id = :recipeId")
  int[] getIngredientIds(int recipeId);

  @Query("select * from recipe_ingredient_table where recipe_id = :recipeId")
  RecipeIngredient[] getRecipeIngredients(int recipeId);
}