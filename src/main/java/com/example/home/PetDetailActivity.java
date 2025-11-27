package com.example.home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class PetDetailActivity extends AppCompatActivity {

    private ImageView imagePetDetail;
    private TextView textNameDetail, textBreedDetail, textAgeDetail, textGenderDetail;
    private TextView textPriceDetail, textDescriptionDetail, textAddress, textPhone, textCreatedDate;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        initViews();
        
        displayPetDetails();
    }

    private void initViews() {
        imagePetDetail = findViewById(R.id.imagePetDetail);
        textNameDetail = findViewById(R.id.textNameDetail);
        textBreedDetail = findViewById(R.id.textBreedDetail);
        textAgeDetail = findViewById(R.id.textAgeDetail);
        textGenderDetail = findViewById(R.id.textGenderDetail);
        textPriceDetail = findViewById(R.id.textPriceDetail);
        textDescriptionDetail = findViewById(R.id.textDescriptionDetail);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);
        textCreatedDate = findViewById(R.id.textCreatedDate);
        chipGroup = findViewById(R.id.chipGroup);
    }

    private void displayPetDetails() {
        String petName = getIntent().getStringExtra("pet_name");
        String petType = getIntent().getStringExtra("pet_type");
        String petBreed = getIntent().getStringExtra("pet_breed");
        int petAge = getIntent().getIntExtra("pet_age", 0);
        String petGender = getIntent().getStringExtra("pet_gender");
        double petPrice = getIntent().getDoubleExtra("pet_price", 0);
        String petDescription = getIntent().getStringExtra("pet_description");
        String petImageUrl = getIntent().getStringExtra("pet_image_url");
        String petSize = getIntent().getStringExtra("pet_size");
        String petHairLength = getIntent().getStringExtra("pet_hair_length");
        String petColor = getIntent().getStringExtra("pet_color");
        String petAddress = getIntent().getStringExtra("pet_address");
        String petPhone = getIntent().getStringExtra("pet_phone");
        String petCreatedDate = getIntent().getStringExtra("pet_created_date");

        textNameDetail.setText(petName);
        textBreedDetail.setText(petBreed);
        textAgeDetail.setText(petAge + " месяцев");
        textGenderDetail.setText("male".equals(petGender) ? "Мальчик" : "Девочка");
        textPriceDetail.setText(petPrice + " руб.");
        textDescriptionDetail.setText(petDescription);
        textAddress.setText(petAddress);
        textPhone.setText(petPhone);
        textCreatedDate.setText(petCreatedDate);

        addChips(petType, petSize, petAge, petHairLength, petColor);

        if (petImageUrl != null && !petImageUrl.isEmpty()) {
            int resourceId = getResources().getIdentifier(
                    petImageUrl,
                    "drawable",
                    getPackageName()
            );
            if (resourceId != 0) {
                imagePetDetail.setImageResource(resourceId);
            }
        }
    }

    private void addChips(String type, String size, int age, String hairLength, String color) {
        if (type != null && !type.isEmpty()) {
            addChip("dog".equals(type) ? "Собака" : "Кошка", "#4CAF50");
        }
        if (size != null && !size.isEmpty()) {
            addChip(size, "#2196F3");
        }
        String ageText = age + " месяцев";
        addChip(ageText, "#FF9800");
        if (hairLength != null && !hairLength.isEmpty()) {
            addChip(hairLength, "#9C27B0");
        }
        if (color != null && !color.isEmpty()) {
            addChip(color, "#F44336");
        }
    }

    private void addChip(String text, String color) {
        Chip chip = new Chip(this);
        chip.setText(text);
        chip.setChipBackgroundColorResource(android.R.color.white);
        chip.setTextColor(android.graphics.Color.parseColor(color));
        chip.setChipStrokeColorResource(android.R.color.darker_gray);
        chip.setChipStrokeWidth(2f);
        chip.setClickable(false);
        chip.setFocusable(false);
        chipGroup.addView(chip);
    }
}