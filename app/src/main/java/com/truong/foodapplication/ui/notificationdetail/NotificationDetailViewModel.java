package com.truong.foodapplication.ui.notificationdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class NotificationDetailViewModel extends ViewModel {
    private MutableLiveData<List<Notification>> sharedData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;
    public NotificationDetailViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        setShareNotificationData();
    }
    public NotificationDetailViewModel(){
        setShareNotificationData();
    }
    public void setShareNotificationData() {
        baseMainActivityViewModel.getSharedNotificationData().observeForever(new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notificationList) {
                sharedData.setValue(notificationList);
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

}