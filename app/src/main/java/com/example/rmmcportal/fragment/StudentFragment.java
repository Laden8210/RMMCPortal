package com.example.rmmcportal.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.StudentScheduleAdapter;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;

import java.util.ArrayList;
import java.util.List;


public class StudentFragment extends Fragment implements FirestoreCallback {


    FirestoreRepositoryImpl<ClassSchedule> classScheduleFirestoreRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);
    private List<ClassSchedule> classScheduleList = new ArrayList<>();

    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        classScheduleFirestoreRepository.readAll(this);
        return view;
    }


    @Override
    public void onSuccess(Object result) {
        classScheduleList = (List<ClassSchedule>) result;
        StudentScheduleAdapter studentScheduleAdapter = new StudentScheduleAdapter(getContext(), classScheduleList);
        recyclerView.setAdapter(studentScheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onFailure(Exception e) {

    }
}