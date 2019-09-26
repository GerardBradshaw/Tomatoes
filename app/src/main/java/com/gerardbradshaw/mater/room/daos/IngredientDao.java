package com.gerardbradshaw.mater.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gerardbradshaw.mater.room.entities.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertIngredient(Ingredient ingredient);

  @Delete
  void deleteIngredient(Ingredient... ingredient);

  @Update
  void updateIngredient(Ingredient ingredient);

  // - - - - - - - - - - - - - - - LiveData queries - - - - - - - - - - - - - - -

  @Query("select * from ingredient_table order by name ASC")
  LiveData<List<Ingredient>> getLiveAllIngredients();


  // - - - - - - - - - - - - - - - Non-LiveData queries - - - - - - - - - - - - - - -

  @Query("select * from ingredient_table where name = :ingredientName limit 1")
  Ingredient getIngredient(String ingredientName);

  @Query("select * from ingredient_table where ingredient_id = :ingredientId")
  Ingredient getIngredient(int ingredientId);

  @Query("select ingredient_id from ingredient_table where name = :ingredientName")
  int getIngredientId(String ingredientName);

  @Query("select * from ingredient_table limit 1")
  Ingredient getAnyIngredient();


  // Allergen queries

  @Query("select containsMilk from ingredient_table where name = :ingredientName")
  String getContainsMilk(String ingredientName);

  @Query("select containsEgg from ingredient_table where name = :ingredientName")
  String getContainsEgg(String ingredientName);

  @Query("select containsFish from ingredient_table where name = :ingredientName")
  String getContainsFish(String ingredientName);

  @Query("select containsCrustacean from ingredient_table where name = :ingredientName")
  String getContainsCrustacean(String ingredientName);

  @Query("select containsTreeNuts from ingredient_table where name = :ingredientName")
  String getContainsTreeNuts(String ingredientName);

  @Query("select containsPeanuts from ingredient_table where name = :ingredientName")
  String getContainsPeanuts(String ingredientName);

  @Query("select containsWheat from ingredient_table where name = :ingredientName")
  String getContainsWheat(String ingredientName);

  @Query("select containsSoy from ingredient_table where name = :ingredientName")
  String getContainsSoy(String ingredientName);

  @Query("select containsFodmap from ingredient_table where name = :ingredientName")
  String getContainsFodmap(String ingredientName);

}