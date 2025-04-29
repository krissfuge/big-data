package com.example.views.data.repository;

import com.example.views.data.api.*;

import com.example.views.data.api.EntertainmentApiService;
import com.example.views.data.model.EntertainmentMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class EntertainmentRepository {
    private final EntertainmentApiService apiService;

    public EntertainmentRepository(EntertainmentApiService apiService) {
        this.apiService = apiService;
    }

    public interface ApiCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }

    // Получение всех записей
    public void getAll(ApiCallback<List<EntertainmentMap>> callback) {
        executeCall(apiService.getAll(), callback);
    }

    // Создание новой записи
    public void create(EntertainmentMap place, ApiCallback<EntertainmentMap> callback) {
        executeCall(apiService.create(place), callback);
    }

    // Получение по ID
    public void getById(Long id, ApiCallback<EntertainmentMap> callback) {
        executeCall(apiService.getById(id), callback);
    }

    // Удаление по ID
    public void delete(Long id, ApiCallback<Void> callback) {
        executeCall(apiService.delete(id), callback);
    }

    // Поиск с фильтрами и пагинацией
    public void search(
            String name,
            String category,
            Float minRating,
            int page,
            int size,
            String sortBy,
            SortDirection direction,
            ApiCallback<ApiPageResponse<EntertainmentMap>> callback
    ) {
        executeCall(
                apiService.search(name, category, minRating, page, size, sortBy, direction),
                callback
        );
    }

    // Пагинация
    public void getAllPaged(
            int page,
            int size,
            String sortBy,
            boolean desc,
            ApiCallback<ApiPageResponse<EntertainmentMap>> callback
    ) {
        executeCall(
                apiService.getAllPaged(page, size, sortBy, desc),
                callback
        );
    }

    // Общий метод для выполнения запросов
    private <T> void executeCall(Call<T> call, ApiCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}