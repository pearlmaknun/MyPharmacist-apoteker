package io.pearlmaknun.mypharmacist_apoteker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist_apoteker.R;

public class DashboardFragment extends Fragment {

    @BindView(R.id.nama_user)
    TextView namaUser;
    @BindView(R.id.layout_request)
    CardView layoutRequest;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.rating)
    AppCompatRatingBar rating;
    @BindView(R.id.user_nama_lanjutkan)
    TextView userNamaLanjutkan;
    @BindView(R.id.layout_lanjutkan)
    CardView layoutLanjutkan;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.lanjutkan, R.id.tolak, R.id.terima})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lanjutkan:
                break;
            case R.id.tolak:
                break;
            case R.id.terima:
                break;
        }
    }
}
