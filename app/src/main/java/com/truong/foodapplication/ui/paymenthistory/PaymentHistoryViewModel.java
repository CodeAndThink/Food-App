package com.truong.foodapplication.ui.paymenthistory;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.util.List;

public class PaymentHistoryViewModel extends ViewModel {
    private MutableLiveData<List<PurchaseItem>> order_history = new MutableLiveData<>();
    private BaseMainActivityViewModel baseMainActivityViewModel;

    public PaymentHistoryViewModel(BaseMainActivityViewModel baseMainActivityViewModel) {
        this.baseMainActivityViewModel = baseMainActivityViewModel;
    }
    public PaymentHistoryViewModel(){
    }
    public LiveData<List<PurchaseItem>> getOrderHistory(){
        baseMainActivityViewModel.setOrderHistory();
        baseMainActivityViewModel.getOrderHistory().observeForever(new Observer<List<PurchaseItem>>() {
            @Override
            public void onChanged(List<PurchaseItem> items) {
                order_history.setValue(items);
            }
        });
        return order_history;
    }
}