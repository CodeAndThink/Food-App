package com.truong.foodapplication.mainviewmodel;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.ui.BaseMainActivity;
import com.truong.foodapplication.data.model.User;

import java.util.List;

public class BaseMainActivityViewModel extends ViewModel {
    private SharedViewModel sharedViewModel;
    private MutableLiveData<User> sharedData = new MutableLiveData<>();
    private MutableLiveData<List<Food>> sharedFoodData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private MutableLiveData<List<Notification>> sharedNotificationData = new MutableLiveData<>();
    private FoodsRepository foodsRepository;

    public BaseMainActivityViewModel(BaseMainActivity baseMainActivity, User user) {
        sharedViewModel = new ViewModelProvider(baseMainActivity).get(SharedViewModel.class);
        foodsRepository = new FoodsRepository();
        sharedViewModel.setSharedData(user);
        setSharedFoodData();
        setSharedNotificationData();
    }
    public BaseMainActivityViewModel(){
        foodsRepository = new FoodsRepository();
        setSharedFoodData();
        setSharedNotificationData();
    }
    public void setSharedUserData(User user) {
        sharedData.setValue(user);
    }
    public LiveData<User> getSharedUserData() {
        return sharedData;
    }
    public void setSharedNotificationData() {
        foodsRepository.getNotification().observeForever(new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                sharedNotificationData.setValue(notifications);
                Log.d(TAG, "Nhan du lieu la: " + notifications);
            }
        });
    }
    public LiveData<List<Notification>> getSharedNotificationData(){
        return sharedNotificationData;
    }
    public void setSharedFoodData() {
        foodsRepository.getFoods().observeForever(new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                sharedFoodData.setValue(foods);
                Log.d(TAG, "Nhan du lieu la: " + foods);
            }
        });
    }
    public LiveData<List<Food>> getSharedFoodData(){
        return sharedFoodData;
    }

    public void setPosition(int position){
        this.position.setValue(position);
    }
    public LiveData<Integer> getPosition(){
        return position;
    }

}


