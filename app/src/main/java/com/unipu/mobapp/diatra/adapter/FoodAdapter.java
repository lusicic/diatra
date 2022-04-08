package com.unipu.mobapp.diatra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.Food;
import com.unipu.mobapp.diatra.data.Therapy;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private List<Food> foods = new ArrayList<>();
    private FoodAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Food currentFood = foods.get(position);
        holder.textViewTime.setText(currentFood.getTime());
        holder.textViewType.setText(currentFood.getFoodType());
        holder.textViewAmount.setText(String.valueOf(currentFood.getAmount()));
        holder.textViewIntake.setText(String.valueOf(currentFood.getCalories()));
        holder.textViewCarbs.setText(String.valueOf(currentFood.getCarbs()));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(List<Food> foods){
        this.foods = foods;
        notifyDataSetChanged();
    }

    public Food getFoodAt(int position) { return foods.get(position); }

    public class FoodHolder extends RecyclerView.ViewHolder{
        private TextView textViewTime;
        private TextView textViewType;
        private TextView textViewAmount;
        private TextView textViewIntake;
        private TextView textViewCarbs;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);

            textViewTime = itemView.findViewById(R.id.text_view_food_time);
            textViewType = itemView.findViewById(R.id.text_view_food_type);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewIntake = itemView.findViewById(R.id.text_view_intake);
            textViewCarbs = itemView.findViewById(R.id.text_view_carbs);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(foods.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener){ this.listener = listener;}
}
