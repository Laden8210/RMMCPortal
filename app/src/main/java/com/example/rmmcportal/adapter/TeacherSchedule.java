package com.example.rmmcportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.view.StudentGradeActivity;

import java.util.List;

public class TeacherSchedule extends RecyclerView.Adapter<TeacherSchedule.ViewHolder> {

    private Context context;
    private List<ClassSchedule> schedules;

    public TeacherSchedule(Context context, List<ClassSchedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassSchedule schedule = schedules.get(position);
        holder.tvSubject.setText(schedule.getSubject());
        holder.rvSchedule.setAdapter(new ScheduleAdapter(schedule.getScheduleList()));
        holder.rvSchedule.setLayoutManager(new LinearLayoutManager(context));
        holder.cardView.setOnClickListener(e->{
            Log.d("TeacherSchedule", "onBindViewHolder: " + schedule.getStudentList().size());
            Intent intent = new Intent(context, StudentGradeActivity.class);
            intent.putExtra(ClassSchedule.COLLECTION_NAME, schedule.getUid());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubject;
        private RecyclerView rvSchedule;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubject = itemView.findViewById(R.id.textView);
            rvSchedule = itemView.findViewById(R.id.rvTeacherSchedule);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }

}
