package io.pearlmaknun.mypharmacist_apoteker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist_apoteker.R;
import io.pearlmaknun.mypharmacist_apoteker.model.History;

public class HistoryKonsultasiAdapter extends RecyclerView.Adapter<HistoryKonsultasiAdapter.MyViewHolder> {
    List<History> list;
    Context context;

    HistoryKonsultasiAdapter.OnItemClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.waktu)
        TextView waktu;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.rating)
        AppCompatRatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(final HistoryKonsultasiAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public HistoryKonsultasiAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public HistoryKonsultasiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        HistoryKonsultasiAdapter.MyViewHolder myViewHolder = new HistoryKonsultasiAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final HistoryKonsultasiAdapter.MyViewHolder holder, final int position) {
        final History item = list.get(position);
        holder.nama.setText(item.getUserName());
        holder.waktu.setText(item.getEndChat());
        switch (item.getStatusChat()){
            case "2":
                holder.status.setText("Berlangsung");
                holder.status.setTextColor(context.getResources().getColor(R.color.bg1));
                break;
            case "3":
                holder.status.setText("Expired");
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case "4":
                holder.status.setText("Selesai");
                holder.status.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case "5":
                holder.status.setText("Reported");
                holder.status.setTextColor(context.getResources().getColor(R.color.colorAccent));
                break;
            default:
                break;
        }
        if(item.getRatingStar() != null){
            holder.ratingBar.setRating(Float.valueOf(item.getRatingStar()));
        }
        holder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(History item) {
        list.add(item);
        notifyItemInserted(list.size() + 1);
    }

    public void addAll(List<History> listItem) {
        for (History item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void swap(List<History> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (list != null && list.size() > 0)
            list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public History getItem(int pos) {
        return list.get(pos);
    }

    public String showHourMinute(String hourMinute) {
        String time = "";
        time = hourMinute.substring(0, 5);
        return time;
    }

    public void setFilter(List<History> list) {
        list = new ArrayList<>();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public List<History> getListItem() {
        return list;
    }
}