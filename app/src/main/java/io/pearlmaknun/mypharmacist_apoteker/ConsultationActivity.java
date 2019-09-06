package io.pearlmaknun.mypharmacist_apoteker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist_apoteker.adapter.ChatAdapter;
import io.pearlmaknun.mypharmacist_apoteker.model.Chat;
import io.pearlmaknun.mypharmacist_apoteker.model.Konsultasi;
import io.pearlmaknun.mypharmacist_apoteker.model.User;

public class ConsultationActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.txt_toolbar)
    TextView txtToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_message)
    EditText txtMessage;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    ChatAdapter chatAdapter;
    List<Chat> chats;

    Konsultasi konsultasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        ButterKnife.bind(this);

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");

        recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //linearLayoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .orderByChild("main_id")
                .equalTo(konsultasi.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("user yg konsultasi", dataSnapshot.toString());
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    User user = childSnapshot.getValue(User.class);
                    txtToolbar.setText(user.getUsername());
                    readMessage(konsultasi.getChatId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String konsultasiId) {
        chats = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Chats")
                .orderByChild("id_konsultasi")
                .equalTo(konsultasiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("user yg konsultasi", dataSnapshot.toString());
                chats.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = childSnapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                Log.e("list chat", chats.toString());
                chatAdapter = new ChatAdapter(getApplicationContext(), chats, "");
                recyclerview.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String pesan) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_konsultasi", konsultasi.getChatId());
        hashMap.put("penerima", konsultasi.getUserId());
        hashMap.put("pengirim", konsultasi.getApotekerId());
        hashMap.put("pesan", pesan);

        reference.child("Chats").push().setValue(hashMap);
    }

    @OnClick({R.id.btn_back, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_send:
                String msg = txtMessage.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(msg);
                } else {
                    Toast.makeText(ConsultationActivity.this, "You can't send empty message", Toast.LENGTH_LONG).show();
                }
                txtMessage.setText("");
                break;
        }
    }
}
