package com.gerardbradshaw.tomatoes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gerardbradshaw.tomatoes.R;
import com.gerardbradshaw.tomatoes.helpers.Units;
import com.gerardbradshaw.tomatoes.pojos.RecipeIngredientHolder;
import com.gerardbradshaw.tomatoes.room.RecipeRepository;
import com.gerardbradshaw.tomatoes.room.entities.RecipeIngredient;
import com.gerardbradshaw.tomatoes.room.entities.RecipeStep;
import com.gerardbradshaw.tomatoes.viewholders.RecipeIngredientViewViewHolder;
import com.gerardbradshaw.tomatoes.viewholders.StepViewViewHolder;
import com.gerardbradshaw.tomatoes.viewmodels.ImageViewModel;
import com.gerardbradshaw.tomatoes.viewmodels.RecipeDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

  // - - - - - - - - - - - - - - - Member Variables - - - - - - - - - - - - - - -

  private TextView descriptionView;
  private ImageView imageView;
  private Toolbar toolbar;

  private List<RecipeIngredientViewViewHolder> recipeIngredientViewHolders = new ArrayList<>();
  private List<StepViewViewHolder> stepViewHolders = new ArrayList<>();
  private List<RecipeIngredientHolder> ingredientHolders = new ArrayList<>();

  private RecipeDetailsViewModel detailsViewModel;
  private ImageViewModel imageViewModel;
  private RecipeRepository repository;

  private int recipeId;
  private double servings;
  private Context context;

  private static String LOG_TAG = "GGG - RecipeDetailActivity";

  private String recipeTitle;


  // - - - - - - - - - - - - - - - Activity Methods - - - - - - - - - - - - - - -
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);

    // Get intent information
    Intent receivedIntent = getIntent();
    recipeId = receivedIntent.getIntExtra(MainActivity.EXTRA_RECIPE_ID, 0);
    context = this;

    // Initialize the VMs
    detailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
    imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);

    // Get the number of servings
    // TODO add ability to set number of servings
    servings = 1;

    // Set up Toolbar
    toolbar = findViewById(R.id.recipeDetail_toolbar);
    setSupportActionBar(toolbar);

    // Get a handle to the Views
    imageView = findViewById(R.id.recipeDetail_image);
    descriptionView = findViewById(R.id.recipeDetail_description);

    // Set the title
    detailsViewModel.getTitle(recipeId).observe(this, new Observer<String>() {
      @Override
      public void onChanged(String s) {
        recipeTitle = s;
        toolbar.setTitle(recipeTitle);
        loadImageIntoView();
      }
    });

    // Load the image
    imageViewModel.imageUpdateNotifier().observe(this, new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        loadImageIntoView();
      }
    });

    // Set the description
    detailsViewModel.getDescription(recipeId).observe(this, new Observer<String>() {
      @Override
      public void onChanged(String s) {
        descriptionView.setText(s);
      }
    });

    // Set the ingredients
    detailsViewModel.getIngredients(recipeId).observe(this, new Observer<RecipeIngredient[]>() {
      @Override
      public void onChanged(RecipeIngredient[] recipeIngredients) {
        loadIngredientsIntoView(recipeIngredients);
      }
    });

    // Set the steps
    detailsViewModel.getSteps(recipeId).observe(this, new Observer<RecipeStep[]>() {
      @Override
      public void onChanged(RecipeStep[] recipeSteps) {
        loadStepsIntoView(recipeSteps);
      }
    });
  }

  private void loadImageIntoView() {
    Glide.with(context)
        .load(imageViewModel.getFile(recipeTitle))
        .placeholder(context.getDrawable(R.drawable.img_placeholder_detail_view))
        .into(imageView);
  }

  private void loadIngredientsIntoView(RecipeIngredient[] recipeIngredients) {
    // Clear the ViewHolder references
    recipeIngredientViewHolders.clear();

    // Instantiate a LayoutInflater
    LayoutInflater inflater = (LayoutInflater) getApplicationContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    // Get the insert point
    ViewGroup insertPoint = findViewById(R.id.recipeDetail_ingredientsLayout);

    // Create a RecipeIngredientHolder for each ingredient
    for (RecipeIngredient ingredient : recipeIngredients) {
      String name = detailsViewModel.getIngredient(ingredient.getIngredientId()).getName();
      double amount = ingredient.getAmount() * servings;
      String unit = ingredient.getUnits();
      ingredientHolders.add(new RecipeIngredientHolder(name, amount, unit));
    }

    // Add a new View to the layout for each ingredient
    for(RecipeIngredientHolder holder : ingredientHolders) {
      // Format the amount and set the View String
      String quantity = Units.formatForDetailView(holder.getAmount(), holder.getUnit());
      String viewString = quantity + holder.getName();

      // Inflate the view to be inserted
      LinearLayout ingredientView = (LinearLayout) inflater
          .inflate(R.layout.view_ingredient_detail, insertPoint, false);

      // Get the children of the View
      RadioButton radioButton = (RadioButton) ingredientView.getChildAt(0);
      TextView textView = (TextView) ingredientView.getChildAt(1);

      // Update the views
      textView.setText(viewString);

      // Create an ingredient view and update it
      recipeIngredientViewHolders.add(new RecipeIngredientViewViewHolder(radioButton, textView));

      // Get the index of the view
      int index = recipeIngredientViewHolders.size() - 1;

      // Insert the view into the main view
      insertPoint.addView(ingredientView, index, new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

      ingredientView.invalidate();
    }
  }

  private void loadStepsIntoView(RecipeStep[] recipeSteps) {
    // Clear the ViewHolder references
    stepViewHolders.clear();

    // Instantiate a layout inflater
    LayoutInflater inflater = (LayoutInflater) getApplicationContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    // Get the insert point
    ViewGroup insertPoint = findViewById(R.id.recipeDetail_stepsLayout);

    for (RecipeStep step : recipeSteps) {
      String description = step.getDescription();
      int number = step.getNumber();

      // Inflate the view to be inserted
      LinearLayout stepView = (LinearLayout) inflater
          .inflate(R.layout.view_step_detail, insertPoint, false);

      // Get the children of the view
      TextView numberView = (TextView) stepView.getChildAt(0);
      TextView descriptionView = (TextView) stepView.getChildAt(1);

      // Update the views
      String numberViewString = number + ". ";
      numberView.setText(numberViewString);
      descriptionView.setText(description);

      // Create a step view and update it
      stepViewHolders.add(new StepViewViewHolder(numberView, descriptionView));

      // Get the index of the view
      int index = stepViewHolders.size() - 1;

      // Insert the view into the main view
      insertPoint.addView(stepView, index, new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
  }


}
