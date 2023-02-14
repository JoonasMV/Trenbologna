package com.example.trainer.database.schemas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Workout {
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;

    private List<Exercise> exList;

    private int id;

    private boolean isPreset;

    private int userId;

    public Workout (String name, Date workoutStarted, Date workoutEnded) {
        this.name = name;
        this.workoutStarted = workoutStarted;
        this.workoutEnded = workoutEnded;
        this.isPreset = false;
        this.exList = new ArrayList<>();
    }

    public Workout (String name, Date workoutStarted) {
        this.name = name;
        this.workoutStarted = workoutStarted;
        this.isPreset = false;
        this.exList = new ArrayList<>();
    }

    public Workout (String name) {
        this.name = name;
        this.isPreset = false;
        this.exList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getWorkoutStarted() {
        return workoutStarted;
    }

    public void setWorkoutStarted(Date workoutStarted) {
        this.workoutStarted = workoutStarted;
    }

    public Date getWorkoutEnded() {
        return workoutEnded;
    }

    public void setWorkoutEnded(Date workoutEnded) {
        this.workoutEnded = workoutEnded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPreset() {
        return isPreset;
    }

    public void setPreset(boolean preset) {
        isPreset = preset;
    }

    public List<Exercise> getExList() {
        return exList;
    }

    public void setExList(List<Exercise> exList) {
        this.exList = exList;
    }

    public void addExerciseToList(Exercise exercise){
        exList.add(exercise);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
