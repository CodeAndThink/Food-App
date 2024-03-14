package com.truong.foodapplication.data.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.data.model.User;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FoodsRepository {
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private MutableLiveData<List<Food>> foods;
    private MutableLiveData<List<Notification>> notifications;

    public FoodsRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        long accountCreationDate = currentUser.getMetadata().getCreationTimestamp();
        db = FirebaseFirestore.getInstance();
        notifications = new MutableLiveData<>();
        loadNotification(accountCreationDate);
    }

    public LiveData<List<Food>> getFoods() {
        if (foods == null) {
            foods = new MutableLiveData<>();
            loadFoods();
        }
        return foods;
    }
    public LiveData<List<Notification>> getNotification(){
        return notifications;
    }

    private void loadFoods() {
        db.collection("foods")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Food> foodList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Food food = document.toObject(Food.class);
                                foodList.add(food);
                            }
                            foods.setValue(foodList);
                        } else {
                            Log.e(TAG, "Get data false!" , task.getException());
                        }
                    }
                });
    }
    private void loadNotification(long accountCreationDate) {
        db.collection("notifications")
//                .whereGreaterThan("date", accountCreationDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Notification notification = document.toObject(Notification.class);
                                notificationList.add(notification);
                            }
                            if (notificationList != null){
                                notifications.setValue(notificationList);
                                Log.e(TAG, "Get data successfully!" );
                            }else {
                                Log.e(TAG, "No data!", task.getException());
                            }
                        } else {
                            Log.e(TAG, "Get data failed!", task.getException());
                        }
                    }
                });
    }
    public LiveData<Boolean> registerNewPayment(PurchaseItem items) {
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        db.collection("orders_history")
                .add(items)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        state.setValue(true);
                        Log.d(TAG, "Payment successfully! ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        state.setValue(false);
                        Log.w(TAG, "Error adding payment!", e);
                    }
                });
        return state;
    }
}
