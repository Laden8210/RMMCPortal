package com.example.rmmcportal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> scheduleList;

    public ScheduleAdapter(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void updateScheduleList(List<Schedule> newScheduleList) {
        this.scheduleList = newScheduleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_class_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.tvTimeDay.setText(schedule.getStartTime() +" - " + schedule.getEndTime() + " | " + schedule.getDay());
        holder.tvLocation.setText(schedule.getLocation());
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeDay, tvLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTimeDay = itemView.findViewById(R.id.tvTimeDay);
            tvLocation = itemView.findViewById(R.id.tvLocation);

        }
    }
}
