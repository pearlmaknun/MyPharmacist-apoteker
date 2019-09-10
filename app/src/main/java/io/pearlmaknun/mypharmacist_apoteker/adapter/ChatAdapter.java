package io.pearlmaknun.mypharmacist_apoteker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist_apoteker.R;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chats;
    private String imgUrl;

    Session session;

    public ChatAdapter(Context context, List<Chat> chats, String imgUrl) {
        this.context = context;
        this.chats = chats;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.showMessage.setText(chat.getPesan());

        if (position == chats.size()-1){
            if (chat.getIsseen()){
                holder.isSeen.setText("Seen");
            } else {
                holder.isSeen.setText("Delivered");
            }
        } else {
            holder.isSeen.setVisibility(View.GONE);
        }

        /*if (imgUrl.equals("default")) {
            holder.imgProfile.setImageResource(R.drawable.profile);
        } else {
            Glide.with(context).load(imgUrl).into(holder.imgProfile);
        }*/
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_profile)
        CircleImageView imgProfile;
        @BindView(R.id.show_message)
        TextView showMessage;
        @BindView(R.id.seen)
        TextView isSeen;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        session = new Session(context);
        if (chats.get(position).getPengirim().equals(session.getUser().getApotekerId())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
