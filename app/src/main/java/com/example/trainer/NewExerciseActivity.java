package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

public class NewExerciseActivity extends AppCompatActivity {
    private ExerciseDAO exerciseDAO = new ExerciseDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
    }

    public void toExercises(View v){
        Intent i = new Intent(this, ExerciseListActivity.class);
        startActivity(i);
    }

    public void addExercise(View v){
        TextView exerciseInput = findViewById(R.id.exerciseNameInput);
        String exerciseName = exerciseInput.getText().toString();

        exerciseDAO.addExercise(exerciseName);
    }
}