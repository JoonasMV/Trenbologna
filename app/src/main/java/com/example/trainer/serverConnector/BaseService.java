package com.example.trainer.serverConnector;

import com.example.trainer.schemas.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class BaseService<T> implements DatabaseService<T>{
    protected final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected final Gson gson = new Gson();
    protected final OkHttpClient okHttpClient = new OkHttpClient();
    protected final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     *
     * @param item Item getting saved to server
     * @param URI_PATH Path used for operations
     * @return Returns saved item
     */
    protected T save(T item, String URI_PATH) {
        Future<T> future = executor.submit(() -> {
            RequestBody reqBody = RequestBody.create(gson.toJson(item), JSON);

            Request req = new Request.Builder()
                    .url(URI_PATH)
                    .post(reqBody)
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();

                Type type = new TypeToken<T>() {
                }.getType();

                return gson.fromJson(res.body().string(), type);
            } catch (IOException e) {
                System.out.println("UserService() - post()");
                e.printStackTrace();
                return null;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getById()");
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param id Id of item wanted
     * @param URI_PATH Path used for operations
     * @return Returns item from server
     */
    protected T getById(String id, String URI_PATH) {
        Future<T> future = executor.submit(() -> {
            Request req = new Request.Builder()
                    .url(URI_PATH + "/" + id)
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();

                Type type = new TypeToken<T>() {
                }.getType();

                return gson.fromJson(res.body().string(), type);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getById()");
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param URI_PATH Path used for operations
     * @return Returns items from server
     */
    protected List<T> getAll(String URI_PATH) {
        Future<List<T>> future = executor.submit(() -> {
            Request req = new Request.Builder()
                    .url(URI_PATH)
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();
                String resString = res.body().string();

                Type listType = new TypeToken<List<T>>() {
                }.getType();

                return gson.fromJson(resString, listType);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("UserService - getAll()");
                return null;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getAll()");
            e.printStackTrace();
            return null;
        }
    }
}