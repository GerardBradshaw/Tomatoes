package com.gerardbradshaw.tomatoes.holders;

public class RecipeIngredientHolder {

  // - - - - - - - - - - - - - - - Member variables - - - - - - - - - - - - - - -

  private String name;
  private double amount;
  private String units;


  // - - - - - - - - - - - - - - - Constructor - - - - - - - - - - - - - - -

  public RecipeIngredientHolder() {
  }

  public RecipeIngredientHolder(String name, double amount, Unit unit) {
    this.name = name;
    this.amount = amount;
    this.units = unit.name();
  }


  // - - - - - - - - - - - - - - - Getters - - - - - - - - - - - - - - -


  public String getName() {
    return name;
  }

  public double getAmount() {
    return amount;
  }

  public String getUnits() {
    return units;
  }


  // - - - - - - - - - - - - - - - Setters - - - - - - - - - - - - - - -


  public void setName(String name) {
    this.name = name;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setUnits(String units) {
    this.units = units;
  }



  public enum Unit {
    MILLILITRES,
    DROPS,
    GRAMS,
    KILOGRAMS,
    METRIC_CUPS,
    TEASPOONS,
    TABLESPOONS,
    PINCH,
    NO_UNIT;
  };
}
