package com.example.trainer.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ListOfExercisesAdapter extends RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder> {

    private ArrayList<Exercise> exerciseList;
    private ExerciseDAO exerciseDAO;

    public ListOfExercisesAdapter(ArrayList<Exercise> exerciseList, ExerciseDAO exerciseDAO) {
        this.exerciseList = exerciseList;
        this.exerciseDAO = exerciseDAO;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfExercise;
        private Button deleteExerciseBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.nameOfExercise);
            deleteExerciseBtn = view.findViewById(R.id.deleteExerciseBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfExercise.setText(exerciseList.get(position).getExerciseName());

        holder.deleteExerciseBtn.setOnClickListener(view -> {
            exerciseDAO.deleteExerciseById(exerciseList.get(position).getExerciseId());
            exerciseList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}