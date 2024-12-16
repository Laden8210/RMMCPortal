package com.example.rmmcportal.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rmmcportal.R;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.Course;
import com.example.rmmcportal.model.StudentInformation;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.util.Messenger;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity implements FirestoreCallback {

    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout, studentNumberLayout, birthdate;

    private Button addTeacherButton;
    private Spinner courseSpinner, yearSpinner;

    private FirestoreRepositoryImpl<UserAccount> repository = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);
    private FirestoreRepositoryImpl<StudentInformation> studentRepository = new FirestoreRepositoryImpl<>(StudentInformation.COLLECTION_NAME, StudentInformation.class);

    private FirestoreRepositoryImpl<Course> courseRepository = new FirestoreRepositoryImpl<>(Course.COLLECTION_NAME, Course.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_student);

        firstNameLayout = findViewById(R.id.first_name);
        lastNameLayout = findViewById(R.id.last_name);
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        addTeacherButton = findViewById(R.id.add_teacher);
        studentNumberLayout = findViewById(R.id.student_number);

        courseSpinner = findViewById(R.id.course_spinner);
        yearSpinner = findViewById(R.id.year_spinner);
        birthdate = findViewById(R.id.birthdate);

        courseRepository.readAll(new FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                if (result instanceof ArrayList) {
                    ArrayList<Course> courseList = (ArrayList<Course>) result;

                    // Create the ArrayAdapter directly from the list
                    ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(
                            AddStudentActivity.this,
                            android.R.layout.simple_spinner_item,
                            courseList
                    );
                    courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    courseSpinner.setAdapter(courseArrayAdapter);
                } else {
                    // Handle the case where the result is not an ArrayList
                    Log.e("AddStudentActivity", "Unexpected result type: " + result.getClass().getName());
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("AddStudentActivity", "Error reading courses", e);
            }
        });


        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        addTeacherButton.setOnClickListener(this::addTeacherAction);
    }

    private void addTeacherAction(View view) {
        String firstName = firstNameLayout.getEditText().getText().toString();
        String lastName = lastNameLayout.getEditText().getText().toString();
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        String studentNumber = studentNumberLayout.getEditText().getText().toString();
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        userAccount.setUserType("student");
        userAccount.setStudentNumber(studentNumber);

        StudentInformation studentInformation = new StudentInformation();

        studentInformation.setCourse(courseSpinner.getSelectedItem().toString());
        studentInformation.setYear(yearSpinner.getSelectedItem().toString());
        studentInformation.setStudentNumber(studentNumber);
        studentInformation.setBirthdate(birthdate.getEditText().getText().toString());

        repository.create(userAccount,this );
        studentRepository.create(studentInformation, this);

    }

    @Override
    public void onSuccess(Object result) {
        Messenger.showAlertDialog(this, "Student added", "Student has been added successfully", "OK").show();
    }

    @Override
    public void onFailure(Exception e) {
        Messenger.showAlertDialog(this, "Error", e.getMessage(), "OK").show();
    }
}