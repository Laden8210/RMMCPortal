package com.example.rmmcportal.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.ScheduleAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.model.Schedule;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class TeacherDetailsActivity extends AppCompatActivity implements FirestoreCallback {

    private Button button;

    private UserAccount teacher;

    private TextView tvName;
    private RecyclerView recyclerView;

    FirestoreRepositoryImpl<ClassSchedule> classScheduleFirestoreRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_details);
        button = findViewById(R.id.button);

        tvName = findViewById(R.id.teacherName);

        recyclerView = findViewById(R.id.recycler_view);

        if (getIntent().hasExtra("teacher")) {
            teacher = getIntent().getParcelableExtra("teacher");
            tvName.setText(teacher.getFirstName() + " " + teacher.getLastName());
            Log.d("TeacherDetailsActivity", "onCreate: " + teacher.getId());
        }


        button.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDetailsActivity.this, CreateClassActivity.class);
            intent.putExtra("teacher", teacher.getId());
            startActivity(intent);
        });

        classScheduleFirestoreRepository.readAll("professorUID", teacher.getId(), this);

    }

    @Override
    public void onSuccess(Object result) {
        List<ClassSchedule> classSchedule = (List<ClassSchedule>) result;
        List<Schedule> scheduleList = new ArrayList<>();
        for (ClassSchedule schedule : classSchedule) {
            scheduleList.addAll(schedule.getScheduleList());
            Log.d("TeacherDetailsActivity", "onSuccess: " + schedule.getScheduleList());
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}