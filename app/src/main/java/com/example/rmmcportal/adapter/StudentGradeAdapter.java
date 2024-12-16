package com.example.rmmcportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.model.StudentGrade;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

// Model class for StudentGrade
public class StudentGradeAdapter extends RecyclerView.Adapter<StudentGradeAdapter.StudentGradeViewHolder> {

    private final Context context;
    private ClassSchedule classSchedule;
    private FirestoreRepositoryImpl<UserAccount> repository = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);

    public StudentGradeAdapter(Context context, ClassSchedule classSchedule) {
        this.context = context;
        this.classSchedule = classSchedule;
    }

    @NonNull
    @Override
    public StudentGradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_grade, parent, false);
        return new StudentGradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentGradeViewHolder holder, int position) {
        StudentGrade grade = classSchedule.getStudentList().get(position);

        repository.readByField("studentNumber", grade.getStudentNumber(), new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                UserAccount userAccount = (UserAccount) result;
                holder.tvStudentName.setText(userAccount.getFirstName() + " " + userAccount.getLastName());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        holder.tilPrelim.getEditText().setText(String.valueOf(grade.getPrelim() != null ? grade.getPrelim() : 0));
        holder.tilMidterm.getEditText().setText(String.valueOf(grade.getMidterm() != null ? grade.getMidterm() : 0));
        holder.tilFinal.getEditText().setText(String.valueOf(grade.getFinals() != null ? grade.getFinals() : 0));

    }

    @Override
    public int getItemCount() {
        return classSchedule.getScheduleList().size();
    }

    public void saveAllGrades(RecyclerView recyclerView) {
        FirestoreRepositoryImpl<ClassSchedule> classScheduleRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);

        for (int i = 0; i < classSchedule.getStudentList().size(); i++) {
            StudentGrade grade = classSchedule.getStudentList().get(i);

            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder instanceof StudentGradeViewHolder) {
                StudentGradeViewHolder holder = (StudentGradeViewHolder) viewHolder;

                String prelim = holder.tilPrelim.getEditText() != null ? holder.tilPrelim.getEditText().getText().toString() : "0";
                String midterm = holder.tilMidterm.getEditText() != null ? holder.tilMidterm.getEditText().getText().toString() : "0";
                String finals = holder.tilFinal.getEditText() != null ? holder.tilFinal.getEditText().getText().toString() : "0";

                grade.setPrelim(prelim);
                grade.setMidterm(midterm);
                grade.setFinals(finals);
            }
        }

        classScheduleRepository.update(classSchedule.getUid(), classSchedule, new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                Log.d("StudentGradeAdapter", "ClassSchedule grades updated successfully");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("StudentGradeAdapter", "Error updating class schedule grades", e);
            }
        });
    }




    public static class StudentGradeViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        TextInputLayout tilPrelim, tilMidterm, tilFinal;

        public StudentGradeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tilPrelim = itemView.findViewById(R.id.tilPrelim);
            tilMidterm = itemView.findViewById(R.id.tilMidterm);
            tilFinal = itemView.findViewById(R.id.tilFinal);
        }
    }


}
