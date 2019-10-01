package com.gerardbradshaw.mater.activities.addrecipe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardbradshaw.mater.R;
import com.gerardbradshaw.mater.room.entities.Step;

import java.util.List;

public class AddStepListAdapter
    extends RecyclerView.Adapter<AddStepListAdapter.NewStepViewHolder> {

  // - - - - - - - - - - - - - - - Member variables - - - - - - - - - - - - - - -

  private final LayoutInflater inflater;
  private List<Step> steps; // Cached copy
  private Context context;
  private static String LOG_TAG = "GGG - AddStepListAdapter";

  private StepEditedListener stepEditedListener;


  // - - - - - - - - - - - - - - - Constructor - - - - - - - - - - - - - - -

  public AddStepListAdapter(Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);
  }


  // - - - - - - - - - - - - - - - Adapter methods - - - - - - - - - - - - - - -

  /**
   * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
   *
   * @param parent the ViewGroup into which the new View will be added after it is bound to an adapter position.
   * @param viewType the view type of the new View
   * @return RecipeViewHolder: The inflated view.
   */
  @NonNull
  @Override
  public NewStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.step_input, parent, false);
    return new NewStepViewHolder(itemView, this);
  }

  /**
   * Called by RecyclerView to display the data at the specified position and set up input
   * listeners. This method updates the contents of the itemView to reflect the item at the given
   * position.
   *
   * @param viewHolder the ViewHolder which should be updated to represent the contents of the item at the given position in the data set
   * @param position the position of the item within the adapter's data set
   */
  @Override
  public void onBindViewHolder(@NonNull final NewStepViewHolder viewHolder, int position) {
    if (steps != null) {

      Step step = steps.get(position);
      final String description = step.getDescription();

      if (!description.isEmpty()) {
        viewHolder.description.setText(description);

      } else {
        viewHolder.description.setText(null);
      }

      viewHolder.number.setText(position + 1);

      viewHolder.description.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
          if (stepEditedListener != null) {
            stepEditedListener.onDescriptionEdited(viewHolder.getAdapterPosition(), editable.toString());
          }
        }
      });
    }
  }

  /**
   * Adapter method to return the number of items to be displayed in the RecyclerView.
   * This method is called many times, and when it is first called, this.summaryList has not
   * been updated. This means, initially, it will return zero.
   *
   * @return the number of items
   */
  @Override
  public int getItemCount() {
    if(steps != null) {
      return steps.size();

    } else {
      return 0;
    }
  }


  // - - - - - - - - - - - - - - - Helper methods - - - - - - - - - - - - - - -

  public void setData(List<Step> steps) {
    this.steps = steps;
    notifyDataSetChanged();
  }

  public Step getStepAtPosition(int position) {
    return steps.get(position);
  }

  public void setStepEditedListener(StepEditedListener stepEditedListener) {
    this.stepEditedListener = stepEditedListener;
  }


  // - - - - - - - - - - - - - - - ViewHolder Class - - - - - - - - - - - - - - -

  class NewStepViewHolder extends RecyclerView.ViewHolder {

    // Member variables
    private TextView description;
    private TextView number;
    final AddStepListAdapter adapter;

    // Constructor
    public NewStepViewHolder(@NonNull View itemView, AddStepListAdapter adapter) {
      super(itemView);

      // Initialize the views and adapter.
      description = itemView.findViewById(R.id.stepInput_description);
      number = itemView.findViewById(R.id.stepInput_number);
      this.adapter = adapter;
    }
  }


  // - - - - - - - - - - - - - - - StepModifiedListener Interface - - - - - - - - - - - - - - -

  public interface StepEditedListener {
    void onDescriptionEdited(int position, String newDescription);
  }

}
