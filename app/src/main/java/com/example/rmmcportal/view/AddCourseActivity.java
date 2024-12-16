package com.example.rmmcportal.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.SubjectAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.Course;
import com.example.rmmcportal.model.Subject;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.util.Messenger;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity implements FirestoreCallback {

    private MaterialButton btnSubject;
    private Button btnAddCourse;

    private TextInputLayout tilCourseName;
    private RecyclerView rvSubjects;

    private SubjectAdapter subjectAdapter;

    private Course course;
    private List<Subject> subjects;
    private FirestoreRepositoryImpl<Course> repository = new FirestoreRepositoryImpl<>(Course.COLLECTION_NAME, Course.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);

        btnAddCourse = findViewById(R.id.btnSaveCourse);
        btnSubject = findViewById(R.id.btnSubject);
        tilCourseName = findViewById(R.id.tilCourseName);
        rvSubjects = findViewById(R.id.rvSubjects);

        btnSubject.setOnClickListener(this::addSubject);
        btnAddCourse.setOnClickListener(this::saveCourse);

        course = new Course();

        // Initialize the subjects list and set it to the course
        subjects = new ArrayList<>();
        course.setSubjects(subjects);

        // Pass the same list reference to the adapter
        subjectAdapter = new SubjectAdapter(this, subjects);
        rvSubjects.setAdapter(subjectAdapter);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveCourse(View view) {

        String courseName = tilCourseName.getEditText().getText().toString();

        course.setName(courseName);

        repository.create(course, this);
    }

    private void addSubject(View view) {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_subject, null);

        TextInputLayout etSubjectName = dialogView.findViewById(R.id.subject_name);
        Spinner spYearLevel = dialogView.findViewById(R.id.spYearLevel);

        ArrayAdapter<CharSequence> yearLevelAdapter = ArrayAdapter.createFromResource(
                this, R.array.year_array, android.R.layout.simple_spinner_item);
        yearLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYearLevel.setAdapter(yearLevelAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Subject");
        builder.setView(dialogView);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String subjectName = etSubjectName.getEditText().getText().toString().trim();
            String yearLevel = spYearLevel.getSelectedItem().toString();

            if (!subjectName.isEmpty()) {
                Subject subject = new Subject();
                subject.setName(subjectName);
                subject.setYearLevel(yearLevel);
                subjects.add(subject);
                subjectAdapter.notifyDataSetChanged();
            } else {
                etSubjectName.setError("Subject name is required");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }


    @Override
    public void onSuccess(Object result) {

        Messenger.showAlertDialog(this, "Course added", "Course has been added successfully", "OK").show();
    }

    @Override
    public void onFailure(Exception e) {
        Messenger.showAlertDialog(this, "Error", e.getMessage(), "OK").show();
    }
}