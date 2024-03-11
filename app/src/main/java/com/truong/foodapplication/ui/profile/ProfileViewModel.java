package com.truong.foodapplication.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> sharedData = new MutableLiveData<>();
    private UserRepository userRepository;
    private BaseMainActivityViewModel baseMainActivityViewModel;

    public ProfileViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        userRepository = new UserRepository();
    }
    public ProfileViewModel(){

    }
    public void setSharedUserData(LiveData<User> sharedUserData) {
        sharedUserData.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                sharedData.setValue(user);
            }
        });
    }
    public LiveData<User> getSharedUserData() {
        return sharedData;
    }
    public LiveData<Boolean> deleteUser(){
        return userRepository.deleteUser();
    }
}