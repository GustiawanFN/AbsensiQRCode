package com.fajarnandagusti.sisimangi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.fajarnandagusti.sisimangi.api.ApiService;
import com.fajarnandagusti.sisimangi.api.Server;
import com.fajarnandagusti.sisimangi.model.ResponseData;
import com.fajarnandagusti.sisimangi.pref.PrefManager;
import com.fajarnandagusti.sisimangi.pref.SesionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fajarnandagusti.sisimangi.pref.SesionManager.KEY_NIP;
import static com.fajarnandagusti.sisimangi.pref.SesionManager.KEY_STATUS;
import static com.fajarnandagusti.sisimangi.pref.SesionManager.KEY_USERNAME;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnAbsensi)
    ImageButton btnScan;
    @BindView(R.id.btnLogout)
    ImageButton btnLogout;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtNip)
    TextView txtNip;
    @BindView(R.id.btnPetunjuk)
    ImageButton btnPetunjuk;
    @BindView(R.id.btnTentang)
    ImageButton btnTentang;

    ProgressDialog pDialog;
    IntentIntegrator intentIntegrator;

    ApiService API;
    SesionManager sesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        API = Server.getAPIService();

        sesion = new SesionManager(this);
        HashMap user= sesion.getUserDetails();

//nyoba SESSION
        txtUsername.setText((CharSequence) user.get(KEY_USERNAME));
        txtStatus.setText((CharSequence) user.get(KEY_STATUS));
        txtNip.setText((CharSequence) user.get(KEY_NIP));

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesion.logoutUser();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        btnPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(MainActivity.this, PetunjukActivity.class));
                finish();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){

            if (result.getContents() == null){

                // Toast.makeText(this, "TIDAK ADA DATA", Toast.LENGTH_SHORT).show();

            }else{

                // jika qrcode berisi data
                try{
                    JSONObject object = new JSONObject(result.getContents());
                    String chiper = object.getString("id"); //ambil nilai QRCODE
                    String absent = "";
//                    Toast.makeText(this, "qrcode : " +chiper, Toast.LENGTH_LONG).show();

                    //WAKTU ABSEN
//                    int from = 700;
//                    int to = 715;


                    //WAKTU ABSEN SORE
                    int from= 2030;
                    int to = 2100;

                    int dari = 2210;
                    int ke = 2300;



                    Date date = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);

                    String nip = txtNip.getText().toString();
                    String status = txtStatus.getText().toString();


                    //   Toast.makeText(this, "waktu sekarang"+date,Toast.LENGTH_LONG).show();
                    int t = c.get(Calendar.HOUR_OF_DAY) * 100 + c.get(Calendar.MINUTE); //rumus



                    if (t >= from && t <= to){ //Rentang waktu absen datang
                        absent = "Datang";
                        Absen(absent, chiper, nip, status);

                    }else if (t >= dari && t <= ke){ //Waktu absen pulang
                        absent = "Pulang";
                        Absen(absent, chiper, nip, status);

                    }else {
                      Toast.makeText(this, "Mohon maaf, anda terlambat", Toast.LENGTH_LONG).show();
                      //  Toast.makeText(this, "WaktuSekarang"+t, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();

                }
            }

        }else{

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void Absen(final String absent, final String chiper, final String nip, final String status) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Tunggu...");
        pDialog.show();

        API.absen(absent, chiper, nip,  status).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){
                    ResponseData responseData = response.body();

                    if (responseData.getSuccess().equals("1")){

                        pDialog.cancel();
                        Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();

                    }else {

                        pDialog.cancel();
                        Toast.makeText(MainActivity.this, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                pDialog.cancel();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent setting = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(setting);
            return true;
        }else if (id == R.id.action_about){
            Intent about = new Intent(getApplicationContext(), TentangActivity.class);
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
