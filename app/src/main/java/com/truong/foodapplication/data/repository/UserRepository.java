package com.truong.foodapplication.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;

public class UserRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;
    private static String link = "https://chatapp-41799-default-rtdb.asia-southeast1.firebasedatabase.app";

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance(link).getReference().child("users");
    }

    public LiveData<User> loginUser(String email, String password) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            usersRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String username = dataSnapshot.child("userName").getValue(String.class);
                                        String displayName = dataSnapshot.child("displayName").getValue(String.class);
                                        User user = new User(username, displayName);
                                        userData.setValue(user);
                                    } else {
                                        userData.postValue(null);
                                        Log.d("FirebaseData", "User with ID " + email + " does not exist.");
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("FirebaseError", "Error fetching data", databaseError.toException());
                                    userData.postValue(null);
                                }
                            });
                        }
                    } else {
                        Log.e("LoginError", "Failed to login: " + task.getException());
                        userData.postValue(null);
                    }
                });
        return userData;
    }
    public LiveData<Boolean> registerNewUser(String email, String password, String displayName) {
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task ->  {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                User newUser = new User(firebaseUser.getEmail(), password, displayName);
                                DatabaseReference usersRef = FirebaseDatabase.getInstance(link)
                                        .getReference().child("users");
                                usersRef.child(firebaseUser.getUid()).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("FirebaseData", "Register successfully ");
                                            state.setValue(true);
                                        } else {
                                            state.setValue(false);
                                            Log.e("FirebaseError", "Register false! ");
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.e("FirebaseError", "Register false! ");
                        }
                });
        return state;
    }
    public LiveData<User> updateUser(String password, String displayName) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        User user = new User(currentUser.getEmail(), currentUser.getDisplayName());
        if (currentUser != null) {
            User updatedUser = new User(currentUser.getEmail(), password, displayName);
            currentUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        DatabaseReference usersRef = FirebaseDatabase.getInstance(link).getReference().child("users").child(currentUser.getUid());
                        usersRef.setValue(updatedUser)
                                .addOnCompleteListener(mtask -> {
                                    if (mtask.isSuccessful()) {
                                        Log.d("FirebaseData", "Update successfully ");
                                        userData.setValue(updatedUser);
                                    } else {
                                        userData.postValue(user);
                                        Log.e("FirebaseError", "Update false! ");
                                    }
                                });
                    }else {
                        Log.e("FirebaseError", "Update false! ");
                        userData.postValue(user);
                    }
                }
            });
        } else {
            Log.e("FirebaseError", "Current user is null! ");
            userData.postValue(user);
        }
        return userData;
    }
    public LiveData<Boolean> deleteUser() {
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance(link).getReference().child("users").child(currentUser.getUid());
            usersRef.removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseData", "User deleted successfully");
                            state.setValue(true);
                        } else {
                            Log.e("FirebaseError", "Failed to delete user");
                            state.setValue(false);
                        }
                    });
        } else {
            Log.e("FirebaseError", "Current user is null");
            state.setValue(false);
        }
        return state;
    }

}

