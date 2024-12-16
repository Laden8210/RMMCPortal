package com.example.rmmcportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.model.Schedule;
import com.example.rmmcportal.model.StudentGrade;
import com.example.rmmcportal.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduleAdapter extends RecyclerView.Adapter<StudentScheduleAdapter.MyViewHolder> {

    private Context context;
    private List<ClassSchedule> classScheduleList = new ArrayList<>();

    public StudentScheduleAdapter(Context context, List<ClassSchedule> classScheduleList) {
        this.context = context;
        this.classScheduleList = classScheduleList;
    }

    @NonNull
    @Override
    public StudentScheduleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentScheduleAdapter.MyViewHolder holder, int position) {
        ClassSchedule classSchedule = classScheduleList.get(position);

        holder.tvSubject.setText(classSchedule.getSubject());
        for (StudentGrade studentGrade: classSchedule.getStudentList()) {
            String studentNumber = SessionManager.getInstance(context).getUserAccount().getStudentNumber();
            if (studentNumber.equals(studentGrade.getStudentNumber())) {
                holder.prelim.setText(studentGrade.getPrelim() != null ? studentGrade.getPrelim() : "N/A");
                holder.midterm.setText(studentGrade.getMidterm() != null ? studentGrade.getMidterm() : "N/A");
                holder.finals.setText(studentGrade.getFinals() != null ? studentGrade.getFinals() : "N/A");

                holder.tvSubject.setText(classSchedule.getSubject());

                for (Schedule schedule: classSchedule.getScheduleList()){
                    TextView tvSchedule = new TextView(context);

                    tvSchedule.setText(schedule.getDay() + " " + schedule.getStartTime() + " - " + schedule.getEndTime() + " |" + schedule.getLocation());
                    holder.tableLayout.addView(tvSchedule);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return classScheduleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSubject, prelim, midterm, finals;
        private TableLayout tableLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tableLayout = itemView.findViewById(R.id.table_layout);
            prelim = itemView.findViewById(R.id.prelim_grade);
            midterm = itemView.findViewById(R.id.midterm_grade);
            finals = itemView.findViewById(R.id.final_grade);
        }
    }
}
