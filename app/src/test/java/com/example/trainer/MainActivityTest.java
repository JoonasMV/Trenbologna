package com.example.trainer;

import com.example.trainer.UI.LoginPage_activity;
import com.example.trainer.UI.MainActivity;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

import static org.mockito.Mockito.*;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void activityStarts(){
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            assertThat(activity).isNotNull();
        }
    }

    @Test
    public void loginPageIsVisible() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            Intent expectedIntent = new Intent(activity, LoginPage_activity.class);
            Intent actual = shadowOf(activity).getNextStartedActivity();
            assertThat(expectedIntent.getComponent()).isEqualTo(actual.getComponent());
        }
    }

    @Test
    public void loginWorks() {
        TrainerController mockController = mock(WorkoutController.class);
        BaseController.setController(mockController);
        try (ActivityController<LoginPage_activity> controller = Robolectric.buildActivity(LoginPage_activity.class)) {
            controller.setup();
            LoginPage_activity activity = controller.get();

            EditText nameInput = activity.findViewById(R.id.nameInput);
            EditText passwordInput = activity.findViewById(R.id.passwordInput);

            nameInput.setText("test");
            passwordInput.setText("yeet");
            activity.findViewById(R.id.signUpButton).performClick();

            verify(mockController, times(1)).authenticateUser(isA(User.class));
            assertThat(shadowOf(activity).getNextStartedActivity().getComponent().getClassName()).isEqualTo(MainActivity.class.getName());
        }
    }

}
