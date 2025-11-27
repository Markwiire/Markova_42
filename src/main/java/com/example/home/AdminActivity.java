package com.example.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);


        CardView cardAddPet = findViewById(R.id.cardAddPet);
        CardView cardEditPets = findViewById(R.id.cardEditPets);
        CardView cardViewPets = findViewById(R.id.cardViewPets);
        Button btnLogout = findViewById(R.id.btnLogout);


        cardAddPet.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AddPetActivity.class);
            startActivity(intent);
        });


        cardEditPets.setOnClickListener(v -> {

            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            intent.putExtra("admin_edit_mode", true);
            startActivity(intent);
        });


        cardViewPets.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intent);
        });


        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}