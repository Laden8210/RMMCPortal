package com.example.rmmcportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.Subject;
import com.example.rmmcportal.model.UserAccount;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.TeacherViewHolder> {

    private Context context;
    private List<Subject> teacherList;

    public SubjectAdapter(Context context, List<Subject> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {

        Subject teacher = teacherList.get(position);
        holder.firstNameTextView.setText(teacher.getName());
        holder.emailTextView.setText(teacher.getYearLevel());
    }

    public void setSubjectList(List<Subject> teacherList) {
        this.teacherList.clear();
        this.teacherList = teacherList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {

        TextView firstNameTextView;

        TextView emailTextView;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.tvSubjectName);

            emailTextView = itemView.findViewById(R.id.tvYearLevel);
        }
    }
}
