package com.truong.foodapplication.ui.fooddetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class FoodDetailViewModel extends ViewModel {
    private MutableLiveData<List<Food>> sharedData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;
    public FoodDetailViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        setShareFoodData();
    }
    public void setShareFoodData() {
        baseMainActivityViewModel.getSharedFoodData().observeForever(new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                sharedData.setValue(foods);
            }
        });;
    }
    public LiveData<List<Food>> getSharedFoodData() {
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
    public void setPayment(String name, String size, int quantity, double price){
        Item item = new Item(name, size, quantity, price);
        baseMainActivityViewModel.addPurchaseItems(item);
    }
}