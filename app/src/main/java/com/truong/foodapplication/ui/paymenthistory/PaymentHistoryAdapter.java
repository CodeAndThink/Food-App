package com.truong.foodapplication.ui.paymenthistory;

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
import com.truong.foodapplication.data.model.PurchaseItem;
import com.truong.foodapplication.ui.purchase.PurchaseAdapter;
import com.truong.foodapplication.ui.purchase.PurchaseViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {
    private List<PurchaseItem> mData;
    private LayoutInflater mInflater;
    private PaymentHistoryAdapter.ItemClickListener mClickListener;
    private int mquantity = 1;
    // Constructor
    PaymentHistoryAdapter(Context context, List<PurchaseItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData= data;
    }

    // Inflate layout từ XML khi cần và trả về ViewHolder mới
    @NonNull
    @Override
    public PaymentHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_history_cardview, parent, false);
        return new ViewHolder(view);
    }

    // Liên kết dữ liệu của mục với mục hiện tại trong RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.ViewHolder holder, int position) {
        PurchaseItem purchaseItem = mData.get(position);
        Date date = purchaseItem.getDate();
        // Định dạng lại Date thành chuỗi "dd/mm/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        holder.date.setText(dateString);
        holder.information.setText(purchaseItem.toString());
        double sum = 0;
        for (int i = 0; i < purchaseItem.getItems().size(); i++){
            sum += purchaseItem.getItems().get(i).getQuatity()*purchaseItem.getItems().get(i).getFoodprice();
        }
        holder.price.setText(String.valueOf(sum));
    }

    // Trả về số lượng mục trong danh sách
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Lưu và tái sử dụng các View trong RecyclerView để tiết kiệm bộ nhớ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date;
        TextView information;
        TextView price;
        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.order_history_food_date);
            information = itemView.findViewById(R.id.order_history_food_information);
            price = itemView.findViewById(R.id.order_history_food_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // Phương thức để cập nhật dữ liệu mới
    public void updateData(List<PurchaseItem> newData) {
        mData.clear(); // Xóa dữ liệu cũ
        mData.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
    }
    // Phương thức cho phép click vào item
    void setClickListener(PaymentHistoryAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Giao diện để xử lý sự kiện click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
