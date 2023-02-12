package com.example.trainer.workouts.currentWorkout;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutManager {

    private final static WorkoutManager instance = new WorkoutManager();

    private Workout workout = null;
    ExerciseDAO exerciseDAO = new ExerciseDAO();
    WorkoutDAO workoutDAO = new WorkoutDAO();

    private WorkoutManager(){}

    public static WorkoutManager getInstance() {
        return instance;
    }

    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

    public void cancelWorkout() {
        this.workout = null;
    }

    public void saveWorkout() {
        workoutDAO.add(workout);
        this.workout = null;
    }

    public boolean workoutActive() {
        return this.workout != null;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public void initWorkout(){

    }

    public Workout getWorkout()  {
        return this.workout;
    }

    public void addSet(int exercisePosition){
       this.workout.getExList().get(exercisePosition).getSetList().add(new ExerciseSet());
    }

    public void addExercise(Exercise exercise) {
        this.workout.addExerciseToList(exercise);
    }



}
