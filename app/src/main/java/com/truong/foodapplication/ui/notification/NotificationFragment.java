package com.truong.foodapplication.ui.notification;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.databinding.FragmentHomeBinding;
import com.truong.foodapplication.databinding.FragmentNotificationBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.home.HomeAdapter;
import com.truong.foodapplication.ui.home.HomeFragment;
import com.truong.foodapplication.ui.home.HomeViewModel;
import com.truong.foodapplication.ui.notificationdetail.NotificationDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    private FragmentNotificationBinding binding;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private NotificationDetailFragment notificationDetailFragment;
    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new NotificationViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        recyclerView = binding.notificationRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo adapter một lần duy nhất
        notificationAdapter = new NotificationAdapter(getActivity(), new ArrayList<>());
        notificationAdapter.setClickListener(NotificationFragment.this::onItemClick);
        recyclerView.setAdapter(notificationAdapter);

        mViewModel.getSharedNotificationData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notificationList) {
                if (notificationList != null){
                    notificationAdapter.updateData(notificationList);
                    Log.e(TAG, "Dữ liệu: " + notificationList);
                } else {
                    Log.e(TAG, "Chưa nhận được dữ liệu!");
                }
            }
        });
        return view;
    }

    private void onItemClick(View view, int i) {
        notificationDetailFragment = new NotificationDetailFragment();
        mViewModel.setPosition(i);
        changeFragment(notificationDetailFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}