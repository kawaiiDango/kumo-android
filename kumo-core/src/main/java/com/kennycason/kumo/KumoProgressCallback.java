package com.kennycason.kumo;

public interface KumoProgressCallback {
    void onProgress(int currentItem, boolean placed, int total);
}
