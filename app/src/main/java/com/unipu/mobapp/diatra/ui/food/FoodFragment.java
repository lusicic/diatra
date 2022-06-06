package com.unipu.mobapp.diatra.ui.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.adapter.FoodAdapter;
import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.List;

public class FoodFragment extends Fragment {

    private DayViewModel dayViewModel;

    private FloatingActionButton buttonNewFood;
    private TextView textViewIntakeSum;
    private TextView textViewCarbsSum;
    private TextView textViewFoodDate;

    private RecyclerView recyclerViewFood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);

        String date = dayViewModel.getDate().getValue();
        String formattedDate = CalendarUtils.dayMonth(date);
        textViewFoodDate.setText(formattedDate);

        textViewIntakeSum.setText(String.valueOf(dayViewModel.getTotalDayCalories().getValue()));
        textViewCarbsSum.setText(String.valueOf(dayViewModel.getTotalDayCarbs().getValue()));

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFood.setHasFixedSize(true);
        recyclerViewFood.setOverScrollMode(View.OVER_SCROLL_NEVER);

        FoodAdapter foodAdapter = new FoodAdapter();
        recyclerViewFood.setAdapter(foodAdapter);

        dayViewModel.getDayFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setFoods(foods);
            }
        });

        buttonNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_foodFragment_to_addNewFoodFragment);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dayViewModel.deleteFood(foodAdapter.getFoodAt(viewHolder.getAdapterPosition()));
                dayViewModel.deleteFirebaseFood(date, String.valueOf(foodAdapter.getFoodAt(viewHolder.getAdapterPosition()).getId()));
                Toast.makeText(getActivity().getApplicationContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewFood);;

        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {

                dayViewModel.setSFood(food);
                Navigation.findNavController(view).navigate(R.id.action_foodFragment_to_addNewFoodFragment);
            }
        });
    }

    private void initViewModel() {
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        recyclerViewFood = view.findViewById(R.id.recycler_view_food);
        buttonNewFood = view.findViewById(R.id.button_new_food);
        textViewIntakeSum = view.findViewById(R.id.text_view_sum_intake);
        textViewCarbsSum = view.findViewById(R.id.text_view_sum_carbs);
        textViewFoodDate = view.findViewById(R.id.text_view_food_date);

    }
}