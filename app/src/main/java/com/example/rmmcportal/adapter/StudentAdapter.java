package com.example.rmmcportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.UserAccount;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewholder> {

    private Context context;
    private List<UserAccount> studentList;

    public StudentAdapter(Context context, List<UserAccount> studentList) {
        this.context = context;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public StudentAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewholder holder, int position) {
        UserAccount student = studentList.get(position);
        holder.firstNameTextView.setText(student.getFirstName() + " " + student.getLastName());
        holder.emailTextView.setText(student.getEmail());
        holder.lastNameTextView.setText(student.getStudentNumber());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public List<UserAccount> getStudentList() {
        return studentList;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView firstNameTextView;
        TextView lastNameTextView;

        TextView emailTextView;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.first_name);
            lastNameTextView = itemView.findViewById(R.id.last_name);
            emailTextView = itemView.findViewById(R.id.email);
        }
    }
}
