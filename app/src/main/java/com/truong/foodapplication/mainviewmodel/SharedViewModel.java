package com.truong.foodapplication.mainviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.User;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<User> sharedData;
    public SharedViewModel(){
        sharedData = new MutableLiveData<>();
    }
    public void setSharedData(User user){
        this.sharedData.setValue(user);
    }
    public MutableLiveData<User> getSharedData(){
        return sharedData;
    }
}
