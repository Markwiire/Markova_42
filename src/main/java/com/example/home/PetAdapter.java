package com.example.home;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private MainActivity context;
    private boolean isAdminMode;

    public PetAdapter(List<Pet> petList, MainActivity context, boolean isAdminMode) {
        this.petList = petList;
        this.context = context;
        this.isAdminMode = isAdminMode;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);


        holder.textName.setText(pet.getName());
        holder.textBreed.setText(pet.getBreed());
        holder.textAge.setText(pet.getAge() + " месяцев");
        holder.textPrice.setText(pet.getPrice() + " руб.");


        if (pet.getImageUrl() != null && !pet.getImageUrl().isEmpty()) {
            int resourceId = context.getResources().getIdentifier(
                    pet.getImageUrl(),
                    "drawable",
                    context.getPackageName()
            );
            if (resourceId != 0) {
                holder.imagePet.setImageResource(resourceId);
            } else {
                holder.imagePet.setImageResource(R.drawable.placeholder_pet);
            }
        } else {
            holder.imagePet.setImageResource(R.drawable.placeholder_pet);
        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PetDetailActivity.class);
            intent.putExtra("pet_id", pet.getId());
            intent.putExtra("pet_name", pet.getName());
            intent.putExtra("pet_type", pet.getType());
            intent.putExtra("pet_breed", pet.getBreed());
            intent.putExtra("pet_age", pet.getAge());
            intent.putExtra("pet_gender", pet.getGender());
            intent.putExtra("pet_description", pet.getDescription());
            intent.putExtra("pet_price", pet.getPrice());
            intent.putExtra("pet_image_url", pet.getImageUrl());
            intent.putExtra("pet_size", pet.getSize());
            intent.putExtra("pet_hair_length", pet.getHairLength());
            intent.putExtra("pet_color", pet.getColor());
            intent.putExtra("pet_address", pet.getAddress());
            intent.putExtra("pet_phone", pet.getPhone());
            intent.putExtra("pet_created_date", pet.getCreatedDate());
            context.startActivity(intent);
        });


        if (isAdminMode) {
            holder.itemView.setOnLongClickListener(v -> {
                showAdminOptions(pet);
                return true;
            });
        }
    }


    private void showAdminOptions(Pet pet) {
        String[] options = {"Редактировать питомца"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Действия администратора")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openEditPetActivity(pet);
                    }
                })
                .setNegativeButton("Отмена", null);

        AlertDialog dialog = builder.create();
        dialog.show();


        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#7E44E0"));
    }


    private void openEditPetActivity(Pet pet) {
        Intent intent = new Intent(context, EditPetActivity.class);
        intent.putExtra("pet_id", pet.getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePet;
        TextView textName, textBreed, textAge, textPrice;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePet = itemView.findViewById(R.id.imagePet);
            textName = itemView.findViewById(R.id.textName);
            textBreed = itemView.findViewById(R.id.textBreed);
            textAge = itemView.findViewById(R.id.textAge);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }
}