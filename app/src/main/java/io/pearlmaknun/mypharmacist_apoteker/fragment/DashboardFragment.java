package io.pearlmaknun.mypharmacist_apoteker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist_apoteker.ConsultationActivity;
import io.pearlmaknun.mypharmacist_apoteker.HistoryActivity;
import io.pearlmaknun.mypharmacist_apoteker.R;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.CheckActivityResponse;
import io.pearlmaknun.mypharmacist_apoteker.model.Konseli;
import io.pearlmaknun.mypharmacist_apoteker.model.Konsultasi;
import io.pearlmaknun.mypharmacist_apoteker.model.KonsultasiResponse;
import io.pearlmaknun.mypharmacist_apoteker.model.Resume;
import io.pearlmaknun.mypharmacist_apoteker.util.CommonUtil;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.BERLANGSUNG;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.CHECK;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.DIPROSES;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.DITOLAK;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.RESPOND_REQUEST;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.RESUME;

public class DashboardFragment extends Fragment {

    @BindView(R.id.nama_user)
    TextView namaUser;
    @BindView(R.id.layout_request)
    CardView layoutRequest;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.rating)
    AppCompatRatingBar rating;
    @BindView(R.id.ratingangka)
    TextView ratingAngka;
    @BindView(R.id.user_nama_lanjutkan)
    TextView userNamaLanjutkan;
    @BindView(R.id.layout_lanjutkan)
    CardView layoutLanjutkan;

    Session session;

    Konseli konseli;

    Konsultasi konsultasi;

    int konsultasi_total = 0;
    float konsulrating = 0;
    long diff = 0;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        session = new Session(getContext());

        check();
        getResume();

        return view;
    }

    private void check() {
        DialogUtils.openDialog(getContext());
        AndroidNetworking.get(CHECK)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .build()
                .getAsObject(CheckActivityResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof CheckActivityResponse) {
                            CheckActivityResponse response1 = (CheckActivityResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            DialogUtils.closeDialog();
                            if (response1.getStatus()) {
                                Log.e("status layout", response1.getStatus().toString());
                                showLayout(response1.getData().getStatusChat(), response1);
                                konseli = response1.getUser();
                                konsultasi = response1.getData();
                                String currentTime = CommonUtil.getCurrentDate();
                                Log.d("current_time", currentTime);
                                String startDate = konsultasi.getStartChat();
                                Log.d("start_con", "" + startDate);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                try {
                                    Date d2 = format.parse(currentTime);
                                    Date d1 = format.parse(startDate);

                                    diff = d2.getTime() - d1.getTime();
                                    Log.d("diff", "" + diff);

                                    long timeLimit = Long.valueOf(konsultasi.getChatDuration()) * 60 * 1000;
                                    diff = timeLimit - diff;
                                    Log.d("cerecall time", konsultasi.getChatDuration());

                                    if (diff < timeLimit) {
                                        Log.d("CLICKED CHAT", "" + diff);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showLayout("-1", response1);
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Toast.makeText(getContext(), "Tidak ada koneksi", Toast.LENGTH_LONG).show();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    private void respond(String status) {
        DialogUtils.openDialog(getContext());
        AndroidNetworking.put(RESPOND_REQUEST)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addQueryParameter("status", status)
                .build()
                .getAsObject(KonsultasiResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof KonsultasiResponse) {
                            KonsultasiResponse response1 = (KonsultasiResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            DialogUtils.closeDialog();
                            if (response1.getStatus()) {
                                if (response1.getKonsultasi().getStatusChat().equals(BERLANGSUNG)){
                                    Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                                    Konsultasi konsultasi = response1.getKonsultasi();
                                    sendMessageFirstTime(konsultasi.getUserId(), konsultasi.getApotekerId(), konsultasi.getChatId());
                                } else {
                                    Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                                    showLayout("-1", null);
                                }
                            } else {
                                Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
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

    private void sendMessageFirstTime(String user, String apoteker, String konsultasiId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_konsultasi", konsultasiId);
        hashMap.put("penerima", user);
        hashMap.put("pengirim", apoteker);
        String pesan = "Selamat siang, ";
        pesan = pesan + konseli.getUserName() + ". ";
        pesan = pesan + "Perkenalkan saya " + session.getUser().getApotekerName() + " yang akan mendampingi proses konsultasi anda dalam 30 menit ke dapan. ";
        pesan = pesan + "Ada yang bisa saya bantu?";
        hashMap.put("pesan", pesan);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);
        Intent intent = new Intent(getContext(), ConsultationActivity.class);
        intent.putExtra("konsultasi", konsultasi);
        startActivity(intent);
    }

    private void showLayout(String status, CheckActivityResponse response){
        switch (status){
            case DIPROSES:
                layoutLanjutkan.setVisibility(View.GONE);
                layoutRequest.setVisibility(View.VISIBLE);
                namaUser.setText(response.getUser().getUserName());
                break;
            case BERLANGSUNG:
                layoutLanjutkan.setVisibility(View.VISIBLE);
                layoutRequest.setVisibility(View.GONE);
                userNamaLanjutkan.setText(response.getUser().getUserName());
                break;
            default:
                layoutLanjutkan.setVisibility(View.GONE);
                layoutRequest.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.lanjutkan, R.id.tolak, R.id.terima, R.id.ic_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lanjutkan:
                Intent intent = new Intent(getContext(), ConsultationActivity.class);
                intent.putExtra("konsultasi", konsultasi);
                intent.putExtra("diff", diff);
                startActivity(intent);
                break;
            case R.id.tolak:
                respond(DITOLAK);
                break;
            case R.id.terima:
                respond(BERLANGSUNG);
                break;
            case R.id.ic_history:
                Intent i = new Intent(getContext(), HistoryActivity.class);
                startActivity(i);
                break;
        }
    }

    private void getResume() {
        AndroidNetworking.get(RESUME)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(Resume.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof Resume) {
                            if (((Resume) response).getStatus()) {
                                konsultasi_total = (((Resume) response).getTotalweek());
                                total.setText(""+konsultasi_total);
                                if (((Resume) response).getAverageRating() != null){
                                    konsulrating = (((Resume) response).getAverageRating());
                                    rating.setRating(konsulrating);
                                    double value = Double.valueOf(konsulrating);
                                    double roundOff = (double) Math.round(value * 100) / 100;
                                    ratingAngka.setText(roundOff + "");
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("anError", anError.getErrorBody() + " AND " + anError.getErrorDetail());
                    }
                });

    }
}
