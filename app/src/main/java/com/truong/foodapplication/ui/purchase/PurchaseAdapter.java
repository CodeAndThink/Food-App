package com.truong.foodapplication.ui.purchase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Item;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{
    private List<Item> mData;
    private PurchaseViewModel purchaseViewModel;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int mquantity = 1;
    // Constructor
    PurchaseAdapter(Context context, List<Item> data, PurchaseViewModel purchaseViewModel) {
        this.mInflater = LayoutInflater.from(context);
        this.mData= data;
        this.purchaseViewModel = purchaseViewModel;
    }

    // Inflate layout từ XML khi cần và trả về ViewHolder mới
    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shopping_item_cardview, parent, false);
        return new ViewHolder(view);
    }

    // Liên kết dữ liệu của mục với mục hiện tại trong RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder holder, int position) {
        Item item = mData.get(position);
        Log.d(TAG, "data of adapter: " + mData.get(position).toString());
        holder.nameandsize.setText(String.format("%s, size: %s", item.getFoodName(), item.getFoodSize()));
        mquantity = item.getQuatity();
        holder.quantity.setText(String.valueOf(mquantity));
        DecimalFormat d = new DecimalFormat("#.#");
        String formatted = d.format(item.getFoodprice()*mquantity);
        holder.price.setText(formatted);
        holder.plus.setOnClickListener(v -> {
            double price = item.getFoodprice();
            if (price!=0.0){
                mquantity+=1;
                mData.get(position).setQuatity(mquantity);
                purchaseViewModel.setSharedPurchaseItem(mData);
            }
            double sum = price * mquantity;
            DecimalFormat df = new DecimalFormat("#.#"); // Định dạng để giữ lại hai chữ số sau dấu thập phân
            String formattedNumber = df.format(sum);
            holder.quantity.setText(String.valueOf(mquantity));
            holder.price.setText(formattedNumber);
        });
        holder.minus.setOnClickListener(v -> {
            double price = item.getFoodprice();
            if (mquantity > 1){
                mquantity-=1;
                mData.get(position).setQuatity(mquantity);
                purchaseViewModel.setSharedPurchaseItem(mData);
            }
            double sum = price * mquantity;
            DecimalFormat df = new DecimalFormat("#.#"); // Định dạng để giữ lại hai chữ số sau dấu thập phân
            String formattedNumber = df.format(sum);
            holder.quantity.setText(String.valueOf(mquantity));
            holder.price.setText(formattedNumber);
        });
    }

    // Trả về số lượng mục trong danh sách
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Lưu và tái sử dụng các View trong RecyclerView để tiết kiệm bộ nhớ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameandsize;
        TextView quantity;
        TextView price;
        ImageButton delete;
        ImageButton plus;
        ImageButton minus;
        ViewHolder(View itemView) {
            super(itemView);
            nameandsize = itemView.findViewById(R.id.purchase_food_information);
            quantity = itemView.findViewById(R.id.purchase_quantity);
            price = itemView.findViewById(R.id.purchase_price);
            plus = itemView.findViewById(R.id.purchase_plus_btn);
            minus = itemView.findViewById(R.id.purchase_minus_btn);
            delete = itemView.findViewById(R.id.purchase_delete_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // Phương thức để cập nhật dữ liệu mới
    public void updateData(List<Item> newData) {
        mData.clear(); // Xóa dữ liệu cũ
        mData.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
    }
    // Phương thức cho phép click vào item
    void setClickListener(PurchaseAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Giao diện để xử lý sự kiện click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
