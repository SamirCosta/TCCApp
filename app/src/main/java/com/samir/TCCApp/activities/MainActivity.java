package com.samir.TCCApp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.DAO.ProductDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.classes.Addressess;

import java.util.ArrayList;
import java.util.List;

import static com.samir.TCCApp.DAO.ClientDAO.client;

public class MainActivity extends AppCompatActivity {
    private CardView btHome, btData, btCupons, btAbout;
    private TextView tvHome, tvData, tvCupons, tvAbout, tvName;
    private DrawerLayout drawerLayout;
    public static int page;
    public static ProductDAO productDAO;

    private final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref();
        checkPermissions();

        Addressess addressess = null;
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        FirebaseUser userFace = FirebaseAuth.getInstance().getCurrentUser();
        if (signInAccount != null) {
            tvName.setText(signInAccount.getGivenName());
        }else if (userFace != null) {
            String name = userFace.getDisplayName();
            String[] array = name.split(" ");
            tvName.setText(array[0]);
        }else if (client != null) {
            addressess = client.getAddressess();
            tvName.setText(client.getNameCli());
        }
        if (addressess != null && addressess.getCEP() != null) {
            AddressActivity.addressess = client.getAddressess();
            String vir = ",";
            AddressActivity.addressess.setAddress(addressess.getLogra() + vir + client.getNumEdif() + vir +
                    addressess.getBairro() + vir + addressess.getCidade() + vir + addressess.getCEP());
        }
        else AddressActivity.addressess = new Addressess();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            page = bundle.getInt("shortcut");
        }

        findViewById(R.id.imageViewIc).setOnClickListener(v -> drawerLayout.open());

        findViewById(R.id.viewLogOut).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            String ARQUIVO_LOGIN = "ArqLogin";
            SharedPreferences pref = getSharedPreferences(ARQUIVO_LOGIN, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, SliderIntroActivity.class);
            intent.putExtra("page", 4);
            startActivity(intent);
            finish();
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_certo);

        colorItem(tvHome);

        btHome.setOnClickListener(v -> {
            drawerLayout.close();
            navController.navigate(R.id.homeFragment);
            colorItem(tvHome);

        });

        btData.setOnClickListener(v -> {
            drawerLayout.close();
            navController.navigate(R.id.dadosFragment);
            colorItem(tvData);

        });

        btCupons.setOnClickListener(v -> {
            drawerLayout.close();
            navController.navigate(R.id.cuponsFragment);
            colorItem(tvCupons);

        });

        btAbout.setOnClickListener(v -> {
            drawerLayout.close();
            navController.navigate(R.id.aboutFragment);
            colorItem(tvAbout);

        });

    }

    private void checkPermissions() {
        List<String> perm = new ArrayList<>();
        for (String p : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), p) != PackageManager.PERMISSION_GRANTED) {
                perm.add(p);
            }
        }

        if (!perm.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    perm.toArray(new String[perm.size()]), 1);
        }

    }

    private void ref() {
        btHome = findViewById(R.id.itemHome);
        btData = findViewById(R.id.itemData);
        btCupons = findViewById(R.id.itemCupom);
        btAbout = findViewById(R.id.itemAbout);

        tvHome = findViewById(R.id.tvHome);
        tvData = findViewById(R.id.tvData);
        tvCupons = findViewById(R.id.tvCupons);
        tvAbout = findViewById(R.id.tvAbout);

        drawerLayout = findViewById(R.id.motionLayout);
        tvName = findViewById(R.id.tvNameMenu);
    }

    private void colorItem(TextView tv) {

        switch (tv.getId()) {
            case R.id.tvHome:
                clearColors();
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            case R.id.tvData:
                clearColors();
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            case R.id.tvCupons:
                clearColors();
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            case R.id.tvAbout:
                clearColors();
                tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void clearColors() {
        tvHome.setTextColor(getResources().getColor(android.R.color.black));
        tvData.setTextColor(getResources().getColor(android.R.color.black));
        tvAbout.setTextColor(getResources().getColor(android.R.color.black));
        tvCupons.setTextColor(getResources().getColor(android.R.color.black));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int p : grantResults) {
            if (p == PackageManager.PERMISSION_DENIED) finish();
        }
    }

    /*private Addressess getInternalAddressess() {
        Addressess addressess = null;
        try {
            FileInputStream fis = new FileInputStream(getFileStreamPath(ARQUIVO_ADDRESS));
            ObjectInputStream ois = new ObjectInputStream(fis);
            addressess = (Addressess) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return addressess;
    }*/

}