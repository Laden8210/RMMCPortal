package com.example.rmmcportal.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.R;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.util.Messenger;
import com.google.android.material.textfield.TextInputLayout;

public class AddTeacherActivity extends AppCompatActivity implements FirestoreCallback {

    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout;

    private Button addTeacherButton;

    private FirestoreRepositoryImpl<UserAccount> repository = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_teacher);

        firstNameLayout = findViewById(R.id.first_name);
        lastNameLayout = findViewById(R.id.last_name);
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        addTeacherButton = findViewById(R.id.add_teacher);

        addTeacherButton.setOnClickListener(this::addTeacherAction);



    }

    private void addTeacherAction(View view) {
        String firstName = firstNameLayout.getEditText().getText().toString();
        String lastName = lastNameLayout.getEditText().getText().toString();
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        userAccount.setUserType("teacher");
        repository.create(userAccount,this );
    }

    @Override
    public void onSuccess(Object result) {
        Messenger.showAlertDialog(this, "Teacher added", "Teacher has been added successfully", "OK").show();
    }

    @Override
    public void onFailure(Exception e) {
        Messenger.showAlertDialog(this, "Error", e.getMessage(), "OK").show();
    }
}