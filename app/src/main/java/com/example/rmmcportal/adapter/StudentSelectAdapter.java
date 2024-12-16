package com.example.rmmcportal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class StudentSelectAdapter extends RecyclerView.Adapter<StudentSelectAdapter.StudentViewHolder> {

    private List<UserAccount> studentList;
    private List<UserAccount> selectedStudents;

    public StudentSelectAdapter(List<UserAccount> studentList) {
        this.studentList = studentList;
        this.selectedStudents = new ArrayList<>();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_select, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        UserAccount student = studentList.get(position);
        holder.checkBox.setText(student.getFirstName() + " " + student.getLastName());

        holder.checkBox.setChecked(selectedStudents.contains(student));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedStudents.add(student);
            } else {
                selectedStudents.remove(student);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public List<UserAccount> getSelectedStudents() {
        return selectedStudents;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public StudentViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_select);
        }
    }
}
