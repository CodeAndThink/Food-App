package com.truong.foodapplication.ui.notificationdetail;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.databinding.FragmentFoodDetailBinding;
import com.truong.foodapplication.databinding.FragmentNotificationDetailBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailAdapter;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.fooddetail.FoodDetailViewModel;
import com.truong.foodapplication.ui.home.HomeFragment;
import com.truong.foodapplication.ui.notification.NotificationFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationDetailFragment extends Fragment {

    private NotificationDetailViewModel mViewModel;
    private FragmentNotificationDetailBinding binding;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private int position;
    public static NotificationDetailFragment newInstance() {
        return new NotificationDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new NotificationDetailViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        mViewModel.getPosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                position = integer;
            }
        });

        mViewModel.getSharedNotificationData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notificationList) {
                if (notificationList != null){
                    binding.notificationDetailName.setText(notificationList.get(position).getName());
                    binding.notificationDetailContext.setText(notificationList.get(position).getContext());
                } else {
                    Log.e(TAG, "Chưa nhận được dữ liệu!");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.BackBtn.setOnClickListener(v -> {
            NotificationFragment notificationFragment = NotificationFragment.newInstance();
            changeFragment(notificationFragment);
        });
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}