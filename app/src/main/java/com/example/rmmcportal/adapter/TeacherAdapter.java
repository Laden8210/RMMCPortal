package com.example.rmmcportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.view.TeacherDetailsActivity;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private Context context;
    private List<UserAccount> teacherList;

    public TeacherAdapter(Context context, List<UserAccount> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {

        UserAccount teacher = teacherList.get(position);
        holder.firstNameTextView.setText(teacher.getFirstName() + " " + teacher.getLastName());

        holder.emailTextView.setText(teacher.getEmail());

        holder.cardView.setOnClickListener(e ->{
            Intent intent = new Intent(context, TeacherDetailsActivity.class);
            intent.putExtra("teacher", teacher);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    // ViewHolder class
    public static class TeacherViewHolder extends RecyclerView.ViewHolder {

        TextView firstNameTextView;

        TextView emailTextView;

        CardView cardView;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.first_name);

            emailTextView = itemView.findViewById(R.id.email);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
