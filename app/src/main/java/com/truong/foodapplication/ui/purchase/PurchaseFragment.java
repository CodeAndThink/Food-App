package com.truong.foodapplication.ui.purchase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.databinding.FragmentPurchaseBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.home.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends Fragment{

    private PurchaseViewModel mViewModel;
    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private FragmentPurchaseBinding binding;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private List<Item> mData = new ArrayList<>();
    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new PurchaseViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        recyclerView = binding.purchaseRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo adapter một lần duy nhất
        purchaseAdapter = new PurchaseAdapter(getActivity(), new ArrayList<>(), mViewModel);
        purchaseAdapter.setClickListener(PurchaseFragment.this::onItemClick);
        recyclerView.setAdapter(purchaseAdapter);
        purchaseAdapter.updateData(mViewModel.getShoppingItem().getValue());
        mViewModel.getShoppingItem().observe(requireActivity(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                mData = items;
                purchaseAdapter.updateData(mData);
                double total = 0;
                for (int i = 0; i < mData.size(); i++){
                    total += mData.get(i).getFoodprice()*mData.get(i).getQuatity();
                }
                DecimalFormat df = new DecimalFormat("#.#"); // Định dạng để giữ lại hai chữ số sau dấu thập phân
                String formattedNumber = df.format(total);
                binding.purchasePriceTotal.setText(formattedNumber);
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
        binding.purchaseBtn.setOnClickListener(v -> {
            mViewModel.setPayment().observe(requireActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean){
                        Toast.makeText(requireActivity(), getString(R.string.order_success), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(requireActivity(), getString(R.string.order_false), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        binding.BackBtn.setOnClickListener(v -> {
            FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
            changeFragment(foodDetailFragment);
        });
        binding.cancelPurchaseBtn.setOnClickListener(v -> {
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
}