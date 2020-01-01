package io.pearlmaknun.mypharmacist_apoteker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.CheckActivityResponse;
import io.pearlmaknun.mypharmacist_apoteker.model.Konseli;
import io.pearlmaknun.mypharmacist_apoteker.model.Konsultasi;
import io.pearlmaknun.mypharmacist_apoteker.model.Pertemuan;
import io.pearlmaknun.mypharmacist_apoteker.model.PertemuanResponse;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.CHECK_HAS_APPOINMENT;

public class MainPertemuanActivity extends AppCompatActivity {

    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.tambah)
    Button tambah;
    @BindView(R.id.apoteker_name)
    TextView apotekerName;
    @BindView(R.id.konseli_name)
    TextView konseliName;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.status)
    TextView status;

    Session session;

    Konsultasi konsultasi;
    Pertemuan pertemuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pertemuan);
        ButterKnife.bind(this);

        session = new Session(this);

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");

        check();
    }

    private void check() {
        DialogUtils.openDialog(this);
        AndroidNetworking.get(CHECK_HAS_APPOINMENT + konsultasi.getChatId())
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .build()
                .getAsObject(PertemuanResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        DialogUtils.closeDialog();
                        if (response instanceof PertemuanResponse) {
                            PertemuanResponse response1 = (PertemuanResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                pertemuan = response1.getData();
                                card.setVisibility(View.VISIBLE);
                                apotekerName.setText(pertemuan.getApotekerName());
                                konseliName.setText(pertemuan.getUserName());
                                detail.setText(pertemuan.getPertemuanDetail());
                                status.setText(pertemuan.getPertemuanStatus());
                            } else {
                                card.setVisibility(View.GONE);
                                Toast.makeText(MainPertemuanActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    @OnClick({R.id.card, R.id.tambah})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tambah:
                Intent intent = new Intent(MainPertemuanActivity.this, AppoinmentActivity.class);
                intent.putExtra("konsultasi", konsultasi);
                startActivity(intent);
                break;
            case R.id.card:
                Intent i = new Intent(MainPertemuanActivity.this, AppoinmentActivity.class);
                i.putExtra("konsultasi", konsultasi);
                i.putExtra("pertemuan", pertemuan);
                startActivity(i);
                break;
        }
    }
}
