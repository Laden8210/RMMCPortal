package com.example.rmmcportal.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rmmcportal.R;
import com.example.rmmcportal.adapter.TeacherSchedule;
import com.example.rmmcportal.callback.FirestoreCallback;
import com.example.rmmcportal.model.ClassSchedule;
import com.example.rmmcportal.service.FirestoreRepositoryImpl;
import com.example.rmmcportal.util.SessionManager;

import java.util.List;


public class TeacherFragment extends Fragment implements FirestoreCallback {

    FirestoreRepositoryImpl<ClassSchedule> classScheduleRepository = new FirestoreRepositoryImpl<>(ClassSchedule.COLLECTION_NAME, ClassSchedule.class);

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

        View view =  inflater.inflate(R.layout.fragment_teacher, container, false);
        recyclerView = view.findViewById(R.id.rvTeacherSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        classScheduleRepository.readAll("professorUID", SessionManager.getInstance(getContext()).getUserAccount().getId(), this);
        Log.d("TeacherFragment", "onCreateView: " + SessionManager.getInstance(getContext()).getUserAccount().getId());


        return view;
    }

    @Override
    public void onSuccess(Object result) {
        recyclerView.setAdapter(new TeacherSchedule(getContext(), (List<ClassSchedule>) result));
    }

    @Override
    public void onFailure(Exception e) {

    }
}