package io.pearlmaknun.mypharmacist_apoteker;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist_apoteker.model.Konsultasi;
import io.pearlmaknun.mypharmacist_apoteker.model.Pertemuan;
import io.pearlmaknun.mypharmacist_apoteker.model.ResponsePostJanji;
import io.pearlmaknun.mypharmacist_apoteker.util.CommonUtil;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.APPOINMENT;

public class AppoinmentActivity extends AppCompatActivity {

    @BindView(R.id.apoteker_name)
    TextView apotekerName;
    @BindView(R.id.konseli_name)
    TextView konseliName;
    @BindView(R.id.datetime)
    TextView tanggalJanji;
    @BindView(R.id.et_alamat)
    TextView alamat;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.c_apoteker)
    CheckBox cApoteker;
    @BindView(R.id.lokasi_saat_ini)
    CheckBox lokasiSaatIni;

    Session session;

    Konsultasi konsultasi;
    Pertemuan pertemuan;

    private LatLng lastPosition;
    private GpsTrackers gps;
    double lat, lng;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);
        ButterKnife.bind(this);

        session = new Session(this);

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");

        gps = new GpsTrackers(this);
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                        Log.e("get last loc", ""+lat+lng);
                    }
                });

        if(getIntent().hasExtra("pertemuan")){
            pertemuan = (Pertemuan) getIntent().getSerializableExtra("pertemuan");
            apotekerName.setText(pertemuan.getApotekerName());
            konseliName.setText(pertemuan.getUserName());
            tanggalJanji.setText(pertemuan.getPertemuanWaktu());
            if(pertemuan.getPertemuanDetail() != null)
                detail.setText(pertemuan.getPertemuanDetail());
            alamat.setText(pertemuan.getPertemuanAlamat());
            if(pertemuan.getPertemuanLokasi() != null){
                lokasiSaatIni.setChecked(true);
                Log.e("ADA", "ADA");
            }
            cApoteker.setVisibility(View.GONE);
        } else {
            apotekerName.setVisibility(View.GONE);
            konseliName.setVisibility(View.GONE);
        }
    }

    private void checkValidasi() {
        ArrayList<View> list = new ArrayList<>();
        list.add(tanggalJanji);
        list.add(detail);
        if (CommonUtil.validateEmptyEntries(list)) {
            if(!cApoteker.isChecked()){
                Toast.makeText(AppoinmentActivity.this, "Harap setujui janji yang anda buat.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!lokasiSaatIni.isChecked()){
                Toast.makeText(AppoinmentActivity.this, "Harap centang lokasi saat ini atau mengisi alamat lengkap", Toast.LENGTH_SHORT).show();
                return;
            }
            buatJanji();
        }
    }

    private void buatJanji() {
        DialogUtils.openDialog(this);
        ANRequest.PostRequestBuilder postRequestBuilder = new ANRequest.PostRequestBuilder(APPOINMENT);
        postRequestBuilder
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .addBodyParameter("user_id", konsultasi.getUserId())
                .addBodyParameter("apoteker_id", konsultasi.getApotekerId())
                .addBodyParameter("konsultasi_id", konsultasi.getChatId())
                .addBodyParameter("pertemuan_waktu", tanggalJanji.getText().toString())
                .addBodyParameter("pertemuan_detail", detail.getText().toString());
        if(!alamat.getText().equals("")){
            postRequestBuilder.addBodyParameter("pertemuan_alamat", alamat.getText().toString());
        }
        if(lokasiSaatIni.isChecked()){
            postRequestBuilder.addBodyParameter("pertemuan_lokasi", lat + ", " + lng);
        }
        postRequestBuilder.build()
                .getAsObject(ResponsePostJanji.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ResponsePostJanji) {
                            ResponsePostJanji response1 = (ResponsePostJanji) response;
                            DialogUtils.closeDialog();
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                Toast.makeText(AppoinmentActivity.this, "Janji berhasil dibuat!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AppoinmentActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Toast.makeText(AppoinmentActivity.this, "Terjadi Kesalahan Teknis", Toast.LENGTH_SHORT).show();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    @OnClick(R.id.submit)
    public void onViewClicked(View view) {
        checkValidasi();
    }
}
