package com.example.trainer.UI.exercises.exerciseList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.UI.MainActivity;
import com.example.trainer.R;
import com.example.trainer.UI.exercises.ExerciseManager;
import com.example.trainer.UI.exercises.exerciseChart.ExerciseChart;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.schemas.ExerciseType;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<ExerciseType> exerciseTypes;

    ExerciseManager exerciseManager;

    public ExerciseListAdapter() {
        exerciseTypes = BaseController.getController().getExerciseTypes();
        exerciseManager = ExerciseManager.getInstance();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameOfExercise;
        private final PopupMenu ppMenu;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.nameOfExercise);
            ppMenu = new PopupMenu(view.getContext(), nameOfExercise);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        exerciseTypes = BaseController.getController().getExerciseTypes();
        System.out.println(exerciseTypes.size());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfExercise.setText(exerciseTypes.get(position).getName());

        holder.nameOfExercise.setOnLongClickListener(view -> {
        holder.ppMenu.getMenuInflater().inflate(R.menu.exercise_popup_menu, holder.ppMenu.getMenu());
        holder.ppMenu.setOnMenuItemClickListener(menuItem -> {
            int hPosition = holder.getLayoutPosition();
            deleteExerciseType(hPosition);
            return false;
        });
        holder.ppMenu.show();
            return false;
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exerciseManager.setExerciseType(exerciseTypes.get(holder.getAdapterPosition()));
                ExerciseChart exerciseChart = new ExerciseChart();
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainContainer, exerciseChart,"")
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseTypes.size();
    }

    private void deleteExerciseType(int position) {
        BaseController.getController().deleteExerciseType(exerciseTypes.get(position).getId());
        exerciseTypes.remove(position);
        //TODO: change notifItemRangeChanged
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}


