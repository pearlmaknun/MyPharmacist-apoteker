package io.pearlmaknun.mypharmacist_apoteker.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist_apoteker.LoginActivity;
import io.pearlmaknun.mypharmacist_apoteker.R;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.model.Profile;
import io.pearlmaknun.mypharmacist_apoteker.model.ProfileResponse;
import io.pearlmaknun.mypharmacist_apoteker.model.RegisterResponse;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.LOGOUT;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.PROFIL;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.STATUS_KONSULTASI;

public class ProfileFragment extends Fragment {

    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.status)
    Switch toggleStatus;

    Session session;

    Profile profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        session = new Session(getContext());
        getProfile();
        initView();

        return view;
    }

    private void initView() {
        toggleStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        toggle("1");
                        Log.e("TOGGLE", "ON");
                    } else {
                        toggle("0");
                        Log.e("TOGGLE", "OFF");
                    }
                }
            }
        });
    }

    private void toggle(String status) {
        DialogUtils.openDialog(getActivity());
        AndroidNetworking.put(STATUS_KONSULTASI + session.getUser().getApotekerId())
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .addQueryParameter("status", status)
                .build()
                .getAsObject(RegisterResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof RegisterResponse) {
                            RegisterResponse response1 = (RegisterResponse) response;
                            DialogUtils.closeDialog();
                            if (!response1.getStatus()) {
                                Toast.makeText(getActivity(), "Status gagal diubah !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Toast.makeText(getActivity(), "Mohon maaf, kesalahan teknis !", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void getProfile() {
        AndroidNetworking.get(PROFIL)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(ProfileResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ProfileResponse) {
                            if (((ProfileResponse) response).getStatus()) {
                                profile = (((ProfileResponse) response).getData());
                                username.setText(profile.getApotekerName());
                                email.setText(profile.getApotekerEmail());
                                phone.setText(profile.getApotekerNumber());
                                if (((ProfileResponse) response).getStatusKonsultasi().equalsIgnoreCase("1")) {
                                    toggleStatus.setChecked(true);
                                } else {
                                    toggleStatus.setChecked(false);
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

    private void logout() {
        AndroidNetworking.get(LOGOUT)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(ProfileResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ProfileResponse) {
                            if (((ProfileResponse) response).getStatus()) {
                                FirebaseAuth.getInstance().signOut();
                                session.logoutUser();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                Toast.makeText(getContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("anError", anError.getMessage());
                    }
                });

    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        DialogUtils.dialogYesNo(getActivity(), "Anda yakin ingin keluar ?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
