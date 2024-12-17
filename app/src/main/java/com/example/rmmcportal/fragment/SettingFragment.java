package com.example.rmmcportal.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rmmcportal.MainActivity;
import com.example.rmmcportal.R;
import com.example.rmmcportal.util.SessionManager;

public class SettingFragment extends Fragment {


    private Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(e ->{
            startActivity(new Intent(getActivity(), MainActivity.class));

            SessionManager.getInstance(getActivity()).clearSession();

            getActivity().finish();
        });
        return view;
    }
}