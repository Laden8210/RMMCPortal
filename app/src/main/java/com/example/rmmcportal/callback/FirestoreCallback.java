package com.example.rmmcportal.callback;

public interface FirestoreCallback {
    void onSuccess(Object result);
    void onFailure(Exception e);
}