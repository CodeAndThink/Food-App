package com.truong.foodapplication.mainviewmodel;
import static android.view.View.generateViewId;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.ui.BaseMainActivity;
import com.truong.foodapplication.data.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseMainActivityViewModel extends ViewModel {
    private SharedViewModel sharedViewModel;
    private MutableLiveData<User> sharedData = new MutableLiveData<>();
    private MutableLiveData<List<Food>> sharedFoodData = new MutableLiveData<>();
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private MutableLiveData<List<Notification>> sharedNotificationData = new MutableLiveData<>();
    private MutableLiveData<List<Item>>  purchaseItems = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<PurchaseItem>> order_history = new MutableLiveData<>(new ArrayList<>());
    private FoodsRepository foodsRepository;

    public BaseMainActivityViewModel(BaseMainActivity baseMainActivity, User user) {
        sharedViewModel = new ViewModelProvider(baseMainActivity).get(SharedViewModel.class);
        foodsRepository = new FoodsRepository();
        sharedViewModel.setSharedData(user);
        setSharedFoodData();
        setSharedNotificationData();
        setOrderHistory();
    }
    public BaseMainActivityViewModel(){
        foodsRepository = new FoodsRepository();
        setSharedFoodData();
        setSharedNotificationData();
        setOrderHistory();
    }
    public LiveData<Boolean> setPurchasePayment(){
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        List<Item> items = purchaseItems.getValue(); // Lấy giá trị hiện tại của purchaseItems
        if (items != null && !items.isEmpty()) {
            PurchaseItem purchaseItem = new PurchaseItem(getSharedUserData().getValue().getUserName(), items, Timestamp.from(Instant.now()));
            foodsRepository.registerNewPayment(purchaseItem).observeForever(new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    state.setValue(aBoolean);
                }
            });
        }
        return state;
    }
    public void setPurchaseItems(List<Item> purchaseItems){
        clearPurchaseItem();
        this.purchaseItems.setValue(new ArrayList<>(purchaseItems));
    }
    public void addPurchaseItems(Item item){
        this.purchaseItems.getValue().add(item);;
    }
    public void clearPurchaseItem(){
        this.purchaseItems.getValue().clear();
    }
    public LiveData<List<Item>> getPurchaseItems(){
        return purchaseItems;
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
    public void setOrderHistory(){
        foodsRepository.loadPaymentHistory();
        foodsRepository.getOrderPayment().observeForever(new Observer<List<PurchaseItem>>() {
            @Override
            public void onChanged(List<PurchaseItem> items) {
                order_history.setValue(items);
            }
        });
    }
    public LiveData<List<PurchaseItem>> getOrderHistory(){
        return order_history;
    }
}


