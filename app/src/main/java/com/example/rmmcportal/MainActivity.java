package com.example.rmmcportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.util.SessionManager;
import com.example.rmmcportal.view.FacultyHeroActivity;
import com.example.rmmcportal.view.StudentHeroActivity;
import com.example.rmmcportal.view.TeacherHeroActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProviderFactory;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

public class MainActivity extends AppCompatActivity implements FirestoreCallback {

    private TextInputLayout tilEmail, tilPassword;
    private Button btnLogin;
    private FirestoreRepositoryImpl<UserAccount> repository = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this::loginAction);

        FirebaseApp.getInstance().setAutomaticResourceManagementEnabled(true);
        FirebaseApp.initializeApp(this);
        AppCheckProviderFactory providerFactory = PlayIntegrityAppCheckProviderFactory.getInstance();
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(providerFactory);

        UserAccount userAccount =SessionManager.getInstance(this).getUserAccount();

        if (userAccount == null) {
            return;
        }

        if (userAccount.getUserType() == null) {
            return;
        }

        if (userAccount.getUserType().equals("student")) {
            startActivity(new Intent(this, StudentHeroActivity.class));
            finish();
        } else if (userAccount.getUserType().equals("teacher")){
            startActivity(new Intent(this, TeacherHeroActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, FacultyHeroActivity.class));
            finish();
        }

    }

    private void loginAction(View view) {

        String email = tilEmail.getEditText().getText().toString();

        if (email.isEmpty()) {
            tilEmail.setError("Email is required");
            return;
        }

        repository.readByField("email", email, this);


    }

    @Override
    public void onSuccess(Object result) {
        UserAccount userAccount = (UserAccount) result;

        if (userAccount == null) {
            tilEmail.setError("Invalid email or password");
            return;
        }
        if (userAccount.getPassword().equals(tilPassword.getEditText().getText().toString())) {
            SessionManager.getInstance(this).saveUserAccount(userAccount);
            Log.d("MainActivity", "onSuccess: " + userAccount.getId());
            if (userAccount.getUserType().equals("student")) {
                startActivity(new Intent(this, StudentHeroActivity.class));
            } else if (userAccount.getUserType().equals("teacher")){
                startActivity(new Intent(this, TeacherHeroActivity.class));
            } else {
                startActivity(new Intent(this, FacultyHeroActivity.class));
                finish();
            }
        } else {
            tilEmail.setError("Invalid email or password");
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}