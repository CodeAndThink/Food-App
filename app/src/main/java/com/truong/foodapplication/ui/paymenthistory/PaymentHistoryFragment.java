package com.truong.foodapplication.ui.paymenthistory;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.databinding.FragmentPaymentHistoryBinding;
import com.truong.foodapplication.databinding.FragmentPurchaseBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.home.HomeFragment;
import com.truong.foodapplication.ui.purchase.PurchaseAdapter;
import com.truong.foodapplication.ui.purchase.PurchaseFragment;
import com.truong.foodapplication.ui.purchase.PurchaseViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryFragment extends Fragment {

    private PaymentHistoryViewModel mViewModel;
    private FragmentPaymentHistoryBinding binding;
    public static PaymentHistoryFragment newInstance() {
        return new PaymentHistoryFragment();
    }
    private RecyclerView recyclerView;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private List<PurchaseItem> mData = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new PaymentHistoryViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        recyclerView = binding.historyRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo adapter một lần duy nhất
        paymentHistoryAdapter = new PaymentHistoryAdapter(getActivity(), new ArrayList<>());
        paymentHistoryAdapter.setClickListener(PaymentHistoryFragment.this::onItemClick);
        recyclerView.setAdapter(paymentHistoryAdapter);

        mViewModel.getOrderHistory().observe(requireActivity(), new Observer<List<PurchaseItem>>() {
            @Override
            public void onChanged(List<PurchaseItem> purchaseItems) {
                mData = purchaseItems;
                paymentHistoryAdapter.updateData(mData);
            }
        });
        return view;
    }
    private void onItemClick(View view, int i) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.BackBtn.setOnClickListener(v -> {
            HomeFragment homeFragment = HomeFragment.newInstance();
            changeFragment(homeFragment);
        });
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.getOrderHistory().removeObservers(this);
    }
}