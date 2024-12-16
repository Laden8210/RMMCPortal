package com.example.rmmcportal.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ClassSchedule implements Parcelable {
    public static final String COLLECTION_NAME = "class_schedule";

    private String uid;

    private String subject;

    private String professorUID;

    private List<Schedule> scheduleList;
    private List<StudentGrade> studentList;

    public ClassSchedule() {
        scheduleList = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    protected ClassSchedule(Parcel in) {
        subject = in.readString();
        professorUID = in.readString();
    }

    public static final Creator<ClassSchedule> CREATOR = new Creator<ClassSchedule>() {
        @Override
        public ClassSchedule createFromParcel(Parcel in) {
            return new ClassSchedule(in);
        }

        @Override
        public ClassSchedule[] newArray(int size) {
            return new ClassSchedule[size];
        }
    };


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfessorUID() {
        return professorUID;
    }

    public void setProfessorUID(String professorUID) {
        this.professorUID = professorUID;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<StudentGrade> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentGrade> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(subject);
        dest.writeString(professorUID);
    }
}
