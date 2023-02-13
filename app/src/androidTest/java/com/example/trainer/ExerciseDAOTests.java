package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExerciseDAOTests{
    private static Context ctx;

    private static ExerciseDAO exDao;

    private static WorkoutManager workoutManager;

    private int exerciseTypeId;

    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        DatabaseHelper.initialize(ctx);
        workoutManager = WorkoutManager.getInstance();

        exDao = new ExerciseDAO();

    }

    @Before
    public void beforeEach(){
        exDao.deleteAllExercises();
        exDao.deleteAllExerciseTypes();

        exerciseTypeId = exDao.addExerciseType(new ExerciseType("mockType"));
    }

    @Test
    public void exerciseType_creation() {
        exerciseTypeId = exDao.addExerciseType(new ExerciseType("mock2"));

        ExerciseType type = exDao.getExerciseTypeById(exerciseTypeId);

        assertEquals("mock2", type.getName());
    }

    @Test
    public void exercise_creation(){
        Exercise e = new Exercise(exerciseTypeId);

        int id = exDao.addExercise(e);

        assertNotEquals(-1, id);

        Exercise exFromDb = exDao.getExerciseById(id);

        assertEquals("mockType", exFromDb.getExerciseName());
    }




}
