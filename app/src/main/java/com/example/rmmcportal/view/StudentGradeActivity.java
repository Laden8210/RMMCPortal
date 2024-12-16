package com.example.rmmcportal.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.StudentGradeAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.model.StudentGrade;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;

import java.util.List;

public class StudentGradeActivity extends AppCompatActivity {


    private RecyclerView rvStudentGrade;
    FirestoreRepositoryImpl<ClassSchedule> classScheduleRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_grade);

        rvStudentGrade = findViewById(R.id.rvStudentGrade);

        Button btnSaveAll = findViewById(R.id.btnSaveAll);

        if (getIntent().hasExtra(ClassSchedule.COLLECTION_NAME)) {
            String uid = getIntent().getStringExtra(ClassSchedule.COLLECTION_NAME);

            classScheduleRepository.readById(uid, new FirestoreCallback() {
                @Override
                public void onSuccess(Object result) {
                    ClassSchedule classSchedule = (ClassSchedule) result;
                    StudentGradeAdapter studentGradeAdapter = new StudentGradeAdapter(StudentGradeActivity.this, classSchedule);
                    rvStudentGrade.setAdapter(studentGradeAdapter);
                    rvStudentGrade.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(StudentGradeActivity.this));

                    btnSaveAll.setOnClickListener(v -> {
                        if (studentGradeAdapter != null) {
                            studentGradeAdapter.saveAllGrades(rvStudentGrade);
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }



    }
}