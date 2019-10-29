package io.pearlmaknun.mypharmacist_apoteker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist_apoteker.model.LoginResponse;
import io.pearlmaknun.mypharmacist_apoteker.model.RegisterResponse;
import io.pearlmaknun.mypharmacist_apoteker.util.CommonUtil;
import io.pearlmaknun.mypharmacist_apoteker.util.DialogUtils;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.AKTIVASI;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.LOGIN;
import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.REGISTER;

public class AktivasiActivity extends AppCompatActivity {

    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.con_password)
    TextInputEditText conPassword;

    FirebaseAuth auth;
    DatabaseReference reference;

    private LatLng lastPosition;
    private GpsTrackers gps;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi);
        ButterKnife.bind(this);

        session = new Session(this);

        auth = FirebaseAuth.getInstance();

        gps = new GpsTrackers(this);
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
    }

    private void checkValidasi() {
        ArrayList<View> list = new ArrayList<>();
        list.add(email);
        list.add(password);
        if (CommonUtil.validateEmptyEntries(list)) {
            if (password.getText().toString().equals(conPassword.getText().toString())) {
                aktivasi();
            } else {
                Toast.makeText(AktivasiActivity.this, "Konfirmasi Password tidak sesuai.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void aktivasi() {
        Log.e("password", password.getText().toString());
        DialogUtils.openDialog(this);
        AndroidNetworking.post(AKTIVASI)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("password", password.getText().toString())
                .addQueryParameter("email", email.getText().toString())
                .build()
                .getAsObject(RegisterResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof RegisterResponse) {
                            RegisterResponse response1 = (RegisterResponse) response;
                            //DialogUtils.closeDialog();
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                Toast.makeText(AktivasiActivity.this, "Selamat, pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                                registerFirebase(response1.getUsername(), email.getText().toString(), password.getText().toString(), response1.getId());
                                //login();
                            } else {
                                Toast.makeText(AktivasiActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        if (anError.getErrorCode() == 422) {
                            Toast.makeText(AktivasiActivity.this, "Mohon maaf, email sudah digunakan !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AktivasiActivity.this, "Mohon maaf, kesalahan teknis !", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    private void registerFirebase(final String username, String email, String password, String userMainId) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Apoteker").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("apoteker_id", userMainId);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        login();
                                    }
                                }
                            });
                        } else {
                            DialogUtils.closeDialog();
                            Toast.makeText(AktivasiActivity.this, "You can't registered with this email and password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login() {
        AndroidNetworking.post(LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter("email", email.getText().toString())
                .addBodyParameter("password", password.getText().toString())
                .addBodyParameter("app_version", session.getVersionApp())
                .addBodyParameter("device_id", session.getDeviceId())
                .addBodyParameter("latitude", String.valueOf(lastPosition.latitude))
                .addBodyParameter("longitude", String.valueOf(lastPosition.longitude))
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof LoginResponse) {
                            LoginResponse response1 = (LoginResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                DialogUtils.closeDialog();
                                session.createLoginSession(response1.getData());
                                session.setToken(response1.getToken());
                                Intent intent = new Intent(AktivasiActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
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

    @OnClick({R.id.aktivasi, R.id.masuk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aktivasi:
                checkValidasi();
                break;
            case R.id.masuk:
                Intent intent = new Intent(AktivasiActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}
