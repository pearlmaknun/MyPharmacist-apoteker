package io.pearlmaknun.mypharmacist_apoteker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist_apoteker.adapter.HistoryKonsultasiAdapter;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.History;
import io.pearlmaknun.mypharmacist_apoteker.model.ResponseHistory;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.HISTORY_KONSULTASI;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    Session session;

    HistoryKonsultasiAdapter adapter;

    List<History> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        session = new Session(getApplicationContext());
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        adapter = new HistoryKonsultasiAdapter(getApplicationContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.hasFixedSize();

        getHistory();
    }

    public void getHistory(){
        DialogUtils.openDialog(this);
        AndroidNetworking.get(HISTORY_KONSULTASI)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .build()
                .getAsObject(ResponseHistory.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        DialogUtils.closeDialog();
                        if (response instanceof ResponseHistory) {
                            ResponseHistory response1 = (ResponseHistory) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                list = (((ResponseHistory) response).getData());
                                if (list.size() > 0) {
                                    adapter.swap(list);
                                }
                            } else {
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
}
