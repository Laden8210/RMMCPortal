package com.example.rmmcportal.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rmmcportal.R;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.ScheduleAdapter;
import com.example.rmmcportal.adapter.StudentAdapter;
import com.example.rmmcportal.adapter.StudentSelectAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.model.Course;
import com.example.rmmcportal.model.Schedule;
import com.example.rmmcportal.model.StudentGrade;
import com.example.rmmcportal.model.Subject;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.util.Messenger;

import java.util.ArrayList;
import java.util.List;

public class CreateClassActivity extends AppCompatActivity {

    private Spinner courseSpinner, subjectSpinner;
    private RecyclerView scheduleRecyclerView, studentRecyclerView;
    private Button btnAddSchedule, btnSelectStudent, btnSave;

    private ClassSchedule classSchedule;

    private FirestoreRepositoryImpl<Course> courseRepository;
    private FirestoreRepositoryImpl<UserAccount> studentRepositopry = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);
    private List<UserAccount> allStudents;
    private StudentAdapter studentAdapter;

    private List<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        courseRepository = new FirestoreRepositoryImpl<>(Course.COLLECTION_NAME, Course.class);
        initializeViews();
        setupSpinners();
        setupRecyclerViews();
        setupButtonActions();


    }

    private void initializeViews() {
        courseSpinner = findViewById(R.id.course);
        subjectSpinner = findViewById(R.id.subject);
        scheduleRecyclerView = findViewById(R.id.schedule);
        studentRecyclerView = findViewById(R.id.student);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        btnSelectStudent = findViewById(R.id.btnSelectStudent);
        btnSave = findViewById(R.id.btnSave);

        classSchedule = new ClassSchedule();
    }

    private void setupSpinners() {
        courseRepository.readAll(new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                courses = (List<Course>) result;

                if (courses.isEmpty()) {
                    Toast.makeText(CreateClassActivity.this, "No courses available.", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> courseNames = new ArrayList<>();
                for (Course course : courses) {
                    courseNames.add(course.getName());
                }

                ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(CreateClassActivity.this, android.R.layout.simple_spinner_item, courseNames);
                courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseSpinner.setAdapter(courseAdapter);

                courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Course selectedCourse = courses.get(position);

                        if (selectedCourse.getSubjects().isEmpty()) {
                            Toast.makeText(CreateClassActivity.this, "No subjects available for this course.", Toast.LENGTH_SHORT).show();
                            subjectSpinner.setAdapter(null); // Clear subjects
                            return;
                        }

                        List<String> subjectNames = new ArrayList<>();

                        for (Subject subject : selectedCourse.getSubjects()) {
                            subjectNames.add(subject.getName());
                        }

                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(CreateClassActivity.this, android.R.layout.simple_spinner_item, subjectNames);
                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectSpinner.setAdapter(subjectAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(CreateClassActivity.this, "Failed to load courses. Please try again.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        studentRepositopry.readAll("userType", "student", new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                List<UserAccount> students = (List<UserAccount>) result;
                allStudents = students;
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setupRecyclerViews() {
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(new ArrayList<>());
        scheduleRecyclerView.setAdapter(scheduleAdapter);
    }

    private void setupButtonActions() {
        btnAddSchedule.setOnClickListener(view -> {
            openAddScheduleDialog();
        });

        btnSelectStudent.setOnClickListener(view -> {
            selectStudentAction();
        });

        btnSave.setOnClickListener(view -> {
            saveSchedule();
        });
    }

    private void saveSchedule() {
        ClassSchedule classSchedule = new ClassSchedule();

        Course selectedCourse = courses.get(courseSpinner.getSelectedItemPosition());
        Subject selectedSubject = selectedCourse.getSubjects().get(subjectSpinner.getSelectedItemPosition());

        classSchedule.setSubject(selectedSubject.getName());

        if (getIntent().hasExtra("teacher")){
            String teacher = getIntent().getStringExtra("teacher");
            classSchedule.setProfessorUID(teacher);
        }

        classSchedule.setScheduleList(((ScheduleAdapter) scheduleRecyclerView.getAdapter()).getScheduleList());

        List<StudentGrade> studentList = new ArrayList<>();

        for (UserAccount student : studentAdapter.getStudentList()) {
            StudentGrade studentGrade = new StudentGrade();
            studentGrade.setStudentNumber(student.getStudentNumber());
            studentList.add(studentGrade);
        }
        classSchedule.setStudentList(studentList);

        FirestoreRepositoryImpl<ClassSchedule> classScheduleRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);

        classScheduleRepository.create(classSchedule, new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                Messenger.showAlertDialog(CreateClassActivity.this, "Success", "Class schedule saved successfully.","Ok").show();
            }

            @Override
            public void onFailure(Exception e) {
                Messenger.showAlertDialog(CreateClassActivity.this, "Error", "Failed to save class schedule. Please try again.","Ok").show();
                e.printStackTrace();
            }
        });
    }

    private void selectStudentAction() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_student);
        dialog.setCancelable(true);

        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StudentSelectAdapter adapter = new StudentSelectAdapter(allStudents);
        recyclerView.setAdapter(adapter);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            List<UserAccount> selectedStudents = adapter.getSelectedStudents();

            studentAdapter = new StudentAdapter(this, selectedStudents);
            studentRecyclerView.setAdapter(studentAdapter);
            studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            Toast.makeText(this, "Selected students: " + selectedStudents.size(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openAddScheduleDialog() {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);

        Spinner spinnerStartTime = dialogView.findViewById(R.id.spinnerStartTime);
        Spinner spinnerEndTime = dialogView.findViewById(R.id.spinnerEndTime);
        Spinner spinnerDay = dialogView.findViewById(R.id.spinnerDay);
        EditText inputLocation = dialogView.findViewById(R.id.inputLocation);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time_slots, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartTime.setAdapter(timeAdapter);
        spinnerEndTime.setAdapter(timeAdapter);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);


        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).setTitle("Add Schedule").setPositiveButton("Save", (dialogInterface, i) -> {
            String startTime = spinnerStartTime.getSelectedItem().toString();
            String endTime = spinnerEndTime.getSelectedItem().toString();
            String day = spinnerDay.getSelectedItem().toString();
            String location = inputLocation.getText().toString();

            if (location.isEmpty()) {
                Toast.makeText(this, "Location is required", Toast.LENGTH_SHORT).show();
            } else {
                Schedule schedule = new Schedule();
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
                schedule.setDay(day);
                schedule.setLocation(location);
                classSchedule.getScheduleList().add(schedule);

                ((ScheduleAdapter) scheduleRecyclerView.getAdapter()).updateScheduleList(classSchedule.getScheduleList());
                Toast.makeText(this, "Schedule added", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create();

        dialog.show();
    }
}
