package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String SUPABASE_URL = "https://rifzmuphaemtlmrijaqr.supabase.co/rest/v1/pets";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJpZnptdXBoYWVtdGxtcmlqYXFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjIwNzc3MDAsImV4cCI6MjA3NzY1MzcwMH0.MA5qpZby_xlSAbwS70JfqbOGkRI04DZlb80MPRRP5Lc";

    private RecyclerView recyclerView;
    private List<Pet> petList = new ArrayList<>();
    private boolean isAdminEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewPets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        isAdminEditMode = getIntent().getBooleanExtra("admin_edit_mode", false);


        if (isAdminEditMode) {
            setTitle("Редактирование питомцев");
            Toast.makeText(this, "Долгое нажатие на питомца для редактирования", Toast.LENGTH_LONG).show();
        }


        ImageButton btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        loadPetsFromSupabase();
    }

    private void loadPetsFromSupabase() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SUPABASE_URL)
                .get()
                .addHeader("apikey", SUPABASE_API_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                    Log.e("PETS", "Connection error", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d("PETS", "Response: " + responseBody);

                if (response.isSuccessful()) {
                    try {
                        JSONArray petsArray = new JSONArray(responseBody);
                        petList.clear();

                        for (int i = 0; i < petsArray.length(); i++) {
                            JSONObject petJson = petsArray.getJSONObject(i);
                            Pet pet = new Pet(
                                    petJson.optString("id", ""),
                                    petJson.optString("name", "Неизвестно"),
                                    petJson.optString("type", "other"),
                                    petJson.optString("breed", "Неизвестно"),
                                    petJson.optInt("age", 0),
                                    petJson.optString("gender", "male"),
                                    petJson.optString("description", "Нет описания"),
                                    petJson.optDouble("price", 0.0),
                                    petJson.optString("image_url", ""),
                                    petJson.optString("size", "Не указан"),
                                    petJson.optString("hair_length", "Не указан"),
                                    petJson.optString("color", "Не указан"),
                                    petJson.optString("address", "Адрес не указан"),
                                    petJson.optString("phone", "Телефон не указан"),
                                    petJson.optString("created_date", "Дата не указана")
                            );
                            petList.add(pet);
                        }

                        runOnUiThread(() -> setupRecyclerView());

                    } catch (JSONException e) {
                        Log.e("PETS", "JSON error", e);
                    }
                }
            }
        });
    }

    private void setupRecyclerView() {

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userRole = prefs.getString("user_role", "Client");
        boolean isAdmin = "Admin".equals(userRole);


        boolean showAdminFunctions = isAdminEditMode || isAdmin;

        PetAdapter adapter = new PetAdapter(petList, this, showAdminFunctions);
        recyclerView.setAdapter(adapter);


        if (isAdminEditMode) {
            Toast.makeText(this, "Загружено питомцев для редактирования: " + petList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPetsFromSupabase();
    }
}