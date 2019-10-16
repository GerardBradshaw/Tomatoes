package com.gerardbradshaw.mater.helpers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Categories {

  // - - - - - - - - - - - - - - - Private constructor - - - - - - - - - - - - - - -

  private Categories(){
    throw new RuntimeException("DO NOT INSTANTIATE!");
  }


  // - - - - - - - - - - - - - - - Member variables - - - - - - - - - - - - - - -

  public enum Category {
    NO_CATEGORY,
    FRESH_FRUIT_AND_VEG,
    BAKERY,
    CEREALS_AND_SPREADS,
    CANNED_GOODS,
    INTERNATIONAL_FOOD,
    RICE_AND_PASTA,
    COLD_FOOD,
    OILS_AND_CONDIMENTS,
    BAKING,
    SPICES,
    KITCHEN_GOODS,
    PERSONAL_HYGIENE,
    CLEANING_SUPPLIES,
    SUPPLEMENTS_AND_MEDICINES,
    BEVERAGES,
    JUICES
  }

  private static final BiMap<String, Category> categoryBiMap = HashBiMap.create();

  static {
    categoryBiMap.put("Uncategorised", Category.NO_CATEGORY);
    categoryBiMap.put("Fresh fruit & vegetables", Category.FRESH_FRUIT_AND_VEG);
    categoryBiMap.put("Bakery", Category.BAKERY);
    categoryBiMap.put("Cereals & spreads", Category.CEREALS_AND_SPREADS);
    categoryBiMap.put("Canned goods", Category.CANNED_GOODS);
    categoryBiMap.put("International food", Category.INTERNATIONAL_FOOD);
    categoryBiMap.put("Rice & pasta", Category.RICE_AND_PASTA);
    categoryBiMap.put("Cold food", Category.COLD_FOOD);
    categoryBiMap.put("Oils & condiments", Category.OILS_AND_CONDIMENTS);
    categoryBiMap.put("Baking", Category.BAKING);
    categoryBiMap.put("Spices", Category.SPICES);
    categoryBiMap.put("Kitchen supplies", Category.KITCHEN_GOODS);
    categoryBiMap.put("Personal hygiene", Category.PERSONAL_HYGIENE);
    categoryBiMap.put("Cleaning supplies", Category.CLEANING_SUPPLIES);
    categoryBiMap.put("Supplements & medicines", Category.SUPPLEMENTS_AND_MEDICINES);
    categoryBiMap.put("Beverages", Category.BEVERAGES);
    categoryBiMap.put("Juices", Category.JUICES);
  }


  // - - - - - - - - - - - - - - - Methods - - - - - - - - - - - - - - -

  public static String getUiString(Category category) {
    if (categoryBiMap.containsValue(category)) {
      return categoryBiMap.inverse().get(category);
    } else {
      return categoryBiMap.inverse().get(Category.NO_CATEGORY);
    }
  }

  public static Category getCategory(String categoryString) {
    if (categoryBiMap.containsKey(categoryString)) {
      return categoryBiMap.get(categoryString);
    } else {
      return Category.NO_CATEGORY;
    }
  }

  public static String getUiString(String categoryString) {
    return getUiString(getCategory(categoryString));
  }

  public static List<String> getCategoryList() {
    return new ArrayList<>(categoryBiMap.keySet());
  }
}
