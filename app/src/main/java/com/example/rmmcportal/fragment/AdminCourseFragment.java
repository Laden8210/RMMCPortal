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
import com.example.rmmcportal.adapter.CourseAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.Course;
import com.example.rmmcportal.model.UserAccount;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.view.AddCourseActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;


public class AdminCourseFragment extends Fragment implements FirestoreCallback {

    private ExtendedFloatingActionButton   fab;

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private FirestoreRepositoryImpl<Course> repository = new FirestoreRepositoryImpl<>(Course.COLLECTION_NAME, Course.class);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_admin_course, container, false);

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycler_view);

        fab.setOnClickListener(this::addCourse);

        repository.readAll(this);

        return view;
    }

    private void addCourse(View view) {
        getContext().startActivity(new Intent(getContext(), AddCourseActivity.class));
    }

    @Override
    public void onSuccess(Object result) {
        courseAdapter = new CourseAdapter(getContext(), (List<Course>) result);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onFailure(Exception e) {

    }
}