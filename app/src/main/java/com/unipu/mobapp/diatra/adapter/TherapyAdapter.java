package com.unipu.mobapp.diatra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.Therapy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TherapyAdapter extends RecyclerView.Adapter<TherapyAdapter.TherapyHolder> {
    //initialized on new arraylist, if the list was null before getting LiveData it would crash
    // if there were methods in onbindviewholder and others
    private List<Therapy> therapies = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public TherapyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.therapy_item, parent, false);
        return new TherapyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TherapyHolder holder, int position) {
        Therapy currentTherapy = therapies.get(position);
        holder.textViewTime.setText(currentTherapy.getTime());
        holder.textViewType.setText(currentTherapy.getType());
        holder.textViewDosage.setText(String.valueOf(currentTherapy.getDosage()));
    }

    @Override
    public int getItemCount() {
        return therapies.size();
    }

    public void setTherapies(List<Therapy> therapies) {
        this.therapies = therapies;
        notifyDataSetChanged();
    }

    class TherapyHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;
        private TextView textViewType;
        private TextView textViewDosage;

        public TherapyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.text_view_therapy_time);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewDosage = itemView.findViewById(R.id.text_view_dosage);
        }
    }
}
