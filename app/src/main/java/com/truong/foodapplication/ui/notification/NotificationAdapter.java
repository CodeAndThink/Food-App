package com.truong.foodapplication.ui.notification;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.Notification;
import com.truong.foodapplication.ui.home.HomeAdapter;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> mData;
    private LayoutInflater mInflater;
    private NotificationAdapter.ItemClickListener mClickListener;

    // Constructor
    NotificationAdapter(Context context, List<Notification> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflate layout từ XML khi cần và trả về ViewHolder mới
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notification_cardview, parent, false);
        return new ViewHolder(view);
    }

    // Liên kết dữ liệu của mục với mục hiện tại trong RecyclerView
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification item = mData.get(position);
        Context context = holder.imageView.getContext();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(item.getImageUrl(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imageView.setImageResource(resourceId);
        } else {
            holder.imageView.setImageResource(R.drawable.default_broken_image);
        }
        holder.notificationName.setText(item.getName());
        // Đối tượng Timestamp từ Firestore
        Date date = item.getDate();

        // Định dạng lại Date thành chuỗi "dd/mm/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        holder.notificationDate.setText(dateString);
    }

    // Trả về số lượng mục trong danh sách
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Lưu và tái sử dụng các View trong RecyclerView để tiết kiệm bộ nhớ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView notificationName;
        TextView notificationDate;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.notification_card_image);
            notificationName = itemView.findViewById(R.id.notification_title);
            notificationDate = itemView.findViewById(R.id.notification_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // Phương thức để cập nhật dữ liệu mới
    public void updateData(List<Notification> newData) {
        mData.clear(); // Xóa dữ liệu cũ
        mData.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
    }
    // Phương thức cho phép click vào item
    void setClickListener(NotificationAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Giao diện để xử lý sự kiện click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
