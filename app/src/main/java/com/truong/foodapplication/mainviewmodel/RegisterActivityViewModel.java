package com.truong.foodapplication.mainviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.ui.RegisterActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivityViewModel extends ViewModel {
    private SharedViewModel sharedViewModel;
    private RegisterActivity registerActivity;
    private FirebaseAuth firebaseAuth;
    private UserRepository userRepository;
    public RegisterActivityViewModel(){
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public RegisterActivityViewModel(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
        sharedViewModel = new ViewModelProvider(registerActivity).get(SharedViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository();
    }
    public LiveData<Boolean> registerNewUser(String email, String password, String displayName) {
        return userRepository.registerNewUser(email, password, displayName);
    }
}
