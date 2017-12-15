package hu.bme.aut.myworkouttracker.adapters;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.myworkouttracker.R;

/**
 * Created by Balint on 2017. 12. 02..
 */

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {

    private final List<ExerciseListItem> items;

    public ExerciseListAdapter() {
        items = new ArrayList<>();
    }

    public void addItem(ExerciseListItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeFirstItemFromList() {
        if (!items.isEmpty()) {
            items.remove(0);
        }
    }

    public void update(List<ExerciseListItem> exerciseListItems) {
        items.clear();
        items.addAll(exerciseListItems);
        notifyDataSetChanged();
    }

    @Override
    public ExerciseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_list, parent, false);
        ExerciseListViewHolder viewHolder = new ExerciseListViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExerciseListViewHolder holder, int position) {
        ExerciseListItem item = items.get(position);
        holder.iconImageView.setImageResource(item.getImageResource());
        holder.nameTextView.setText(item.getExerciseName());
        holder.repsTextView.setText(item.getExerciseReps());
        if (position == 0) {
            holder.row_layout.setBackgroundColor(Color.parseColor("#40b203"));
            holder.repsTextView.setTextColor(Color.parseColor("#ebffe0"));
            holder.nameTextView.setTextColor(Color.parseColor("#ebffe0"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ExerciseListViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView repsTextView;
        TextView nameTextView;
        LinearLayout row_layout;

        public ExerciseListViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.ExerciseIconImageView);
            repsTextView = (TextView) itemView.findViewById(R.id.ExerciseRepsTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.ExerciseNameTextView);
            row_layout=(LinearLayout)itemView.findViewById(R.id.row_linLayout);

        }
    }
}
