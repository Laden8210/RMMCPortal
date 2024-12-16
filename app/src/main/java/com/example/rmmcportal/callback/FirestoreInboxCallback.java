package com.example.rmmcportal.callback;

public interface FirestoreInboxCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
