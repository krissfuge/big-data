package com.example.views.data.api;

import com.example.views.data.model.EntertainmentMap;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EntertainmentApiService {

    // Получение всех записей
    @GET("api/entertainment_map")
    Call<List<EntertainmentMap>> getAll();

    // Создание новой записи
    @POST("api/entertainment_map")
    Call<EntertainmentMap> create(@Body EntertainmentMap place);

    // Получение по ID
    @GET("api/entertainment_map/{id}")
    Call<EntertainmentMap> getById(@Path("id") Long id);

    // Удаление по ID
    @DELETE("api/entertainment_map/{id}")
    Call<Void> delete(@Path("id") Long id);

    // Поиск с фильтрами и пагинацией
    @GET("api/entertainment_map/search")
    Call<ApiPageResponse<EntertainmentMap>> search(
            @Query("name") String name,
            @Query("category") String category,
            @Query("minRating") Float minRating,
            @Query("page") int page,
            @Query("size") int size,
            @Query("sortBy") String sortBy,
            @Query("direction") SortDirection direction
    );

    @GET("api/entertainment_map/paged")
    Call<ApiPageResponse<EntertainmentMap>> getAllPaged(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sortBy") String sortBy,
            @Query("desc") boolean desc
    );
}