package com.truong.foodapplication.ui.purchase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class PurchaseViewModel extends ViewModel {
    private MutableLiveData<List<Food>> sharedData = new MutableLiveData<>();
    private MutableLiveData<List<Item>> sharedPurchaseItem = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private MutableLiveData<User> userSharedData = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;
    public PurchaseViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
    }
    public PurchaseViewModel(){

    }
    public LiveData<User> getSharedUserData() {
        return userSharedData;
    }
    public LiveData<List<Item>> getShoppingItem(){
        baseMainActivityViewModel.getPurchaseItems().observeForever(new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                sharedPurchaseItem.setValue(items);
            }
        });
        return sharedPurchaseItem;
    }
    public LiveData<Boolean> setPayment(){
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        baseMainActivityViewModel.setPurchasePayment().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    baseMainActivityViewModel.clearPurchaseItem();
                    state.setValue(true);
                }
            }
        });
        return state;
    }
    public void setSharedPurchaseItem(List<Item> item){
        baseMainActivityViewModel.setPurchaseItems(item);
    }
}