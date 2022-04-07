package com.unipu.mobapp.diatra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.PhysicalActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhysicalActivityAdapter extends RecyclerView.Adapter<PhysicalActivityAdapter.PhysicalActivityHolder> {
    private List<PhysicalActivity> physicalActivities = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @NotNull
    @Override
    public PhysicalActivityHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.physical_activity_item, parent, false);
        return new PhysicalActivityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhysicalActivityHolder holder, int position) {
        PhysicalActivity currentPhysicalActivity = physicalActivities.get(position);
        holder.textViewTypeOfPhysicalActivity.setText(currentPhysicalActivity.getTypeOfActivity());
        holder.textViewDuration.setText(String.valueOf(currentPhysicalActivity.getDuration()));

        if(currentPhysicalActivity.getTypeOfActivity().equals("yoga")){
            holder.imageViewDistance.setVisibility(View.GONE);
            holder.textViewDistance.setVisibility(View.GONE);
        }
        else
            holder.textViewDistance.setText(String.valueOf(currentPhysicalActivity.getDistance()));

        holder.textViewBurntCalories.setText(String.valueOf(currentPhysicalActivity.getBurntCalories()));
        holder.textViewPhysicalActivityTime.setText(currentPhysicalActivity.getTime());
    }

    @Override
    public int getItemCount() {
        return physicalActivities.size();
    }

    public void setPhysicalActivities(List<PhysicalActivity> physicalActivities) {
        this.physicalActivities = physicalActivities;
        notifyDataSetChanged();
    }

    public PhysicalActivity getPhysicalActivityAt(int position) {
        return physicalActivities.get(position);
    }

    class PhysicalActivityHolder extends RecyclerView.ViewHolder {
        private TextView textViewTypeOfPhysicalActivity;
        private TextView textViewDistance;
        private TextView textViewDuration;
        private TextView textViewBurntCalories;
        private TextView textViewPhysicalActivityTime;

        private ImageView imageViewDistance;

        public PhysicalActivityHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTypeOfPhysicalActivity = itemView.findViewById(R.id.text_view_type_of_physical_activity);
            textViewDistance = itemView.findViewById(R.id.text_view_distance);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);
            textViewBurntCalories = itemView.findViewById(R.id.text_view_burnt_calories);
            textViewPhysicalActivityTime = itemView.findViewById(R.id.text_view_physical_activity_time);

            imageViewDistance = itemView.findViewById(R.id.image_distance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(physicalActivities.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PhysicalActivity physicalActivity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}