package com.example.trainer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class UserProfile_fragment extends Fragment {

    private TextView userName;

    private RecyclerView workoutList;

    private String username;

    private final static String USERNAME_KEY = "username";


    public UserProfile_fragment() {
        // Required empty public constructor
    }


    public static UserProfile_fragment newInstance(String username) {
        UserProfile_fragment fragment = new UserProfile_fragment();

        Bundle args = new Bundle();
        args.putString(USERNAME_KEY, username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        userName = view.findViewById(R.id.userName);
        workoutList = view.findViewById(R.id.userWorkouts);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String username = getArguments().getString(USERNAME_KEY);
        Objects.requireNonNull(username);
        userName.setText(username);

        List<Workout> list = new ArrayList<>();
        //placeholders
        //TODO: real values



        /*
        list.add(new Workout("workout", new Date(), new Date()));
        list.add(new Workout("workout2", new Date(), new Date()));
        list.add(new Workout("workout3", new Date(), new Date()));
        */


        UserProfileAdapter adapter = new UserProfileAdapter(list, getContext());
        handleWorkoutFetching(adapter, username);

        workoutList.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutList.setAdapter(adapter);
    }

    private void handleWorkoutFetching(UserProfileAdapter adapter, String username){
        new Thread(() -> {
            Future<List<Workout>> result = BaseController.getController().getSharedWorkoutsAsync(username);
            try {
                List<Workout> workouts = result.get();
                FragmentActivity activity = getActivity();
                if (activity == null) return;
                activity.runOnUiThread(() -> {
                    adapter.update(workouts);
                });
            } catch (InterruptedException | ExecutionException e) {
                Toaster.toast(getContext(), "Failed to load workouts");
            }
        }).start();
    }
}