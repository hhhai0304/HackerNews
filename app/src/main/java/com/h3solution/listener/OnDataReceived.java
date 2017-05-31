package com.h3solution.listener;

/**
 * OnDataReceived
 * Created by HHHai on 30-05-2017.
 */
public interface OnDataReceived {
    void onReceived(String result);
    void onFail(Exception e);
}