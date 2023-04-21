package com.example.trainer.UI.workouts.workoutStats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseSet;


import java.util.List;

public class WorkoutStatsExerciseAdapter extends RecyclerView.Adapter<WorkoutStatsExerciseAdapter.ViewHolder> {

    private final List<Exercise> exercises;


    private Context parentContext;



    public WorkoutStatsExerciseAdapter(List<Exercise> exercises, Context parentContext) {
        this.exercises = exercises;
        this.parentContext = parentContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView exerciseTypeTitle;

        public ImageButton button;
        public RecyclerView setRecyclerView;

        boolean isPressed;
        public ViewHolder(View view) {
            super(view);

            exerciseTypeTitle = view.findViewById(R.id.exerciseType);
            setRecyclerView = view.findViewById(R.id.setList);
            button = view.findViewById(R.id.toggleButton);
        }
    }

    @NonNull
    @Override
    public WorkoutStatsExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parentContext)
                .inflate(R.layout.exercise_type_item, parent, false);

        return new ViewHolder(v);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Exercise exercise = exercises.get(position);

        String str = exercise.getExerciseName();
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        holder.exerciseTypeTitle.setText(cap);

        List<ExerciseSet> setList = exercise.getSets();

        WorkoutStatsSetAdapter adapter = new WorkoutStatsSetAdapter(setList, parentContext);
        holder.setRecyclerView.setLayoutManager(new LinearLayoutManager(parentContext));
        holder.setRecyclerView.setAdapter(adapter);

        holder.setRecyclerView.setVisibility(View.GONE);
        holder.button.setOnClickListener(v -> {
            if(holder.isPressed){
                holder.setRecyclerView.setVisibility(v.GONE);
            }else{
                holder.setRecyclerView.setVisibility(v.VISIBLE);
            }
            holder.isPressed = !holder.isPressed; // reverse
        });

        holder.exerciseTypeTitle.setOnClickListener(v -> {
            if(holder.isPressed){
                holder.setRecyclerView.setVisibility(v.GONE);
            }else{
                holder.setRecyclerView.setVisibility(v.VISIBLE);
            }
            holder.isPressed = !holder.isPressed; // reverse
        });
    }



    @Override
    public int getItemCount() {
        return exercises.size();
    }

}