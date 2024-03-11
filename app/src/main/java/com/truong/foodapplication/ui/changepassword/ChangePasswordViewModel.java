package com.truong.foodapplication.ui.changepassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.optionfunctions.functions;

public class ChangePasswordViewModel extends ViewModel {
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private MutableLiveData<User> sharedData = new MutableLiveData<>();
    private UserRepository userRepository;
    public ChangePasswordViewModel(){
        sharedData = new MutableLiveData<>();
        userRepository = new UserRepository();
    }
    public ChangePasswordViewModel(BaseMainActivityViewModel baseMainActivityViewModel){
        this.baseMainActivityViewModel = baseMainActivityViewModel;
        sharedData = new MutableLiveData<>();
        userRepository = new UserRepository();
    }
    public LiveData<Boolean> updateUser(ChangeInformationFragment changeInformationFragment , String password, String repassword, String displayName){
        MutableLiveData<Boolean> state = new MutableLiveData<>();
        if (functions.checkPasswordValid(password, repassword) && functions.checkDisplayNameValid(displayName)){
            userRepository.updateUser(password, displayName).observe(changeInformationFragment, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user!=null && user!=baseMainActivityViewModel.getSharedUserData().getValue()){
                        baseMainActivityViewModel.setSharedUserData(user);
                        state.postValue(true);
                    }else {
                        state.postValue(false);
                    }
                }
            });
        }
        return state;
    }
}