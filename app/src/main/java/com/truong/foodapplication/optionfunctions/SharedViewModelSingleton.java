package com.truong.foodapplication.optionfunctions;

import com.truong.foodapplication.mainviewmodel.SharedViewModel;

public class SharedViewModelSingleton {
    private static SharedViewModel instance;

    public static synchronized SharedViewModel getInstance() {
        if (instance == null) {
            instance = new SharedViewModel();
        }
        return instance;
    }
}
