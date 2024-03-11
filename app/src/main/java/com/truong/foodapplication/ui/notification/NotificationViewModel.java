package com.truong.foodapplication.ui.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private MutableLiveData<List<Notification>> sharedData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;
    public NotificationViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        setShareNotificationData();
    }
    public NotificationViewModel(){
        setShareNotificationData();
    }
    public void setShareNotificationData() {
        baseMainActivityViewModel.getSharedNotificationData().observeForever(new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                sharedData.setValue(notifications);
            }
        });;
    }
    public LiveData<List<Notification>> getSharedNotificationData() {
        return sharedData;
    }
    public LiveData<Integer> getPosition(){
        baseMainActivityViewModel.getPosition().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                position.setValue(integer);
            }
        });
        return position;
    }
    public void setPosition(int position){
        baseMainActivityViewModel.setPosition(position);
    }
}