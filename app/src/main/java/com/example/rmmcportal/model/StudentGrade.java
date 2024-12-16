package com.example.rmmcportal.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentGrade implements Parcelable {

    private String studentNumber;
    private String prelim;
    private String midterm;
    private String finals;
    private String average;

    public StudentGrade() {
    }

    protected StudentGrade(Parcel in) {
        studentNumber = in.readString();
        prelim = in.readString();
        midterm = in.readString();
        finals = in.readString();
        average = in.readString();
    }

    public static final Creator<StudentGrade> CREATOR = new Creator<StudentGrade>() {
        @Override
        public StudentGrade createFromParcel(Parcel in) {
            return new StudentGrade(in);
        }

        @Override
        public StudentGrade[] newArray(int size) {
            return new StudentGrade[size];
        }
    };

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPrelim() {
        return prelim;
    }

    public void setPrelim(String prelim) {
        this.prelim = prelim;
    }

    public String getMidterm() {
        return midterm;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public String getFinals() {
        return finals;
    }

    public void setFinals(String finals) {
        this.finals = finals;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(studentNumber);
        dest.writeString(prelim);
        dest.writeString(midterm);
        dest.writeString(finals);
        dest.writeString(average);
    }
}
