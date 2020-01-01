package io.pearlmaknun.mypharmacist_apoteker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.Konsultasi;
import io.pearlmaknun.mypharmacist_apoteker.model.Pertemuan;

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
    TextView cApoteker;

    Session session;

    Konsultasi konsultasi;
    Pertemuan pertemuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);
        ButterKnife.bind(this);

        session = new Session(this);

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");
        if(getIntent().hasExtra("pertemuan")){
            pertemuan = (Pertemuan) getIntent().getSerializableExtra("pertemuan");
            apotekerName.setText(pertemuan.getApotekerName());
            konseliName.setText(pertemuan.getUserName());
            tanggalJanji.setText(pertemuan.getPertemuanWaktu());
            if(pertemuan.getPertemuanDetail() != null)
                detail.setText(pertemuan.getPertemuanDetail());
            alamat.setText(pertemuan.getPertemuanAlamat());
        } else {
            apotekerName.setVisibility(View.GONE);
            konseliName.setVisibility(View.GONE);
        }
    }
}
