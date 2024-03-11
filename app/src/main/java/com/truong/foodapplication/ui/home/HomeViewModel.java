package com.truong.foodapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Food>> sharedData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private MutableLiveData<User> userSharedData = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;
    public HomeViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        setShareFoodData();
        setSharedUserData(baseMainActivityViewModel.getSharedUserData());
    }
    public void setSharedUserData(LiveData<User> sharedUserData) {
        sharedUserData.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userSharedData.setValue(user);
            }
        });
    }
    public LiveData<User> getSharedUserData() {
        return userSharedData;
    }
    public void setShareFoodData() {
        baseMainActivityViewModel.getSharedFoodData().observeForever(new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                sharedData.setValue(foods);
            }
        });
    }
    public LiveData<List<Food>> getSharedFoodData() {
        return sharedData;
    }
    public void setPosition(int position){
        baseMainActivityViewModel.setPosition(position);
    }
}