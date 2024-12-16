package com.example.rmmcportal.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.StudentAdapter;
import com.example.rmmcportal.adapter.TeacherAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.view.AddStudentActivity;
import com.example.rmmcportal.view.AddTeacherActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;


public class AdminStudentFragment extends Fragment implements FirestoreCallback {


    private ExtendedFloatingActionButton addTeacherButton;

    private RecyclerView teacherRecyclerView;

    private StudentAdapter teacherAdapter;

    private FirestoreRepositoryImpl<UserAccount> repository = new FirestoreRepositoryImpl<>(UserAccount.COLLECTION_NAME, UserAccount.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_admin_student, container, false);
        addTeacherButton = view.findViewById(R.id.fab);
        addTeacherButton.setOnClickListener(this::addTeacherAction);

        teacherRecyclerView = view.findViewById(R.id.recycler_view);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repository.readAll("userType", "student",   this);
        return view;
    }

    private void addTeacherAction(View view) {
        getContext().startActivity(new Intent(getContext(), AddStudentActivity.class));
    }

    @Override
    public void onSuccess(Object result) {
        List<UserAccount> teacherList = (List<UserAccount>) result;
        teacherAdapter = new StudentAdapter(getContext(),teacherList);
        teacherRecyclerView.setAdapter(teacherAdapter);
    }

    @Override
    public void onFailure(Exception e) {

    }
}