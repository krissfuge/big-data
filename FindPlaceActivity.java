package com.example.views;

import static java.lang.System.exit;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.views.data.api.*;
import com.example.views.data.repository.*;
import com.example.views.data.model.*;


import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FindPlaceActivity extends AppCompatActivity {
    private EditText searchEditText;
    private FloatingActionButton searchButton;
    private EntertainmentRepository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home); // Ваш XML с MapView и поиском

        // Инициализация элементов UI
        searchEditText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.floatingActionButton4);

        // Инициализация репозитория
        repository = new EntertainmentRepository(RetrofitClient.getApiService());

        // Обработчик кнопки поиска
        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().trim();


        if (query.isEmpty()) {
            Toast.makeText(this, "Введите поисковый запрос", Toast.LENGTH_SHORT).show();
            return;
        }

        // Показываем индикатор загрузки (опционально)
        showLoading(true);

        repository.search(
                query,          // name
                null,          // category
                0f,           // minRating
                0,            // page
                20,           // size
                "rating",     // sortBy
                SortDirection.DESC, // direction
                new EntertainmentRepository.ApiCallback<ApiPageResponse<EntertainmentMap>>() {
                    @Override
                    public void onSuccess(ApiPageResponse<EntertainmentMap> page) {
                        runOnUiThread(() -> {
                            showLoading(false);
                            processSearchResults(page.getContent());
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            showLoading(false);
                            Toast.makeText(FindPlaceActivity.this, error, Toast.LENGTH_LONG).show();
                        });
                    }
                }
        );
    }

    private void processSearchResults(List<EntertainmentMap> results) {
        // Здесь ваша логика обработки результатов поиска
        // Например, добавление маркеров на карту

        if (results.isEmpty()) {
            Toast.makeText(this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
            return;
        }

        // Пример обработки первого результата:
        EntertainmentMap firstResult = results.get(0);
        Toast.makeText(this,
                "Найдено: " + firstResult.getName() + " (" + firstResult.getRating() + ")",
                Toast.LENGTH_SHORT).show();

        // Ваш код для работы с картой...
    }

    private void showLoading(boolean show) {
        // Реализуйте отображение/скрытие индикатора загрузки
        if (show) {
            // Показать ProgressBar
        } else {
            // Скрыть ProgressBar
        }
    }
}
