package com.samir.TCCApp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.SliderPagerAdapter;
import com.samir.TCCApp.api.ClientService;
import com.samir.TCCApp.classes.Client;
import com.samir.TCCApp.utils.CustomViewPager;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.samir.TCCApp.DAO.ClientDAO.client;
import static com.samir.TCCApp.adapters.SliderPagerAdapter.view;
import static com.samir.TCCApp.utils.Helper.retrofit;

public class SliderIntroActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "AQUI";
    public static CustomViewPager viewPager;
    private int[] layouts = {R.layout.slider_1, R.layout.slider_2, R.layout.slider_3, R.layout.slider_4, R.layout.slider_cadlogin};
    private LinearLayout linearLayout;
    private int indCount;
    private ImageView[] indicators;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private View viewLogin;
    SliderPagerAdapter sliderPagerAdapter;
    public static ProgressBar progressBar;
    /*private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;*/

    @Override
    protected void onStart() {
        super.onStart();

        /*FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }*/

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_intro);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*View layout = getWindow().getDecorView();
        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
        viewPager = findViewById(R.id.viewPagerSlider);
        linearLayout = findViewById(R.id.indicatorLayout);
        progressBar = findViewById(R.id.progressBarLogin);

        createRequest();
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLogin = layoutInflater.inflate(R.layout.slider_cadlogin, viewPager);

        loginButton = viewLogin.findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("SUCESS", "FOI");
                findViewById(R.id.progressBarLogin).setVisibility(View.VISIBLE);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("SUCESS", "DEU RUIM: " + error);
            }
        });

        /*authStateListener = firebaseAuth -> mAuth.signOut();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {

                }
            }
        };*/

        AnimationDrawable animationDrawable = (AnimationDrawable) viewPager.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        sliderPagerAdapter = new SliderPagerAdapter(layouts, this);
        viewPager.setAdapter(sliderPagerAdapter);

        indCount = sliderPagerAdapter.getCount();
        indicators = new ImageView[indCount];

        addIndicators(0);

        viewPager.addOnPageChangeListener(viewListener);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int page = bundle.getInt("page");
            viewPager.setCurrentItem(page);
        }

    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if (email != null) validateRegisterByEmail(email);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SliderIntroActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Erro ao entrar com Facebook");
                    builder.setMessage("Sua conta do Facebook não está vinculada a um email");
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        LoginManager.getInstance().logOut();
                        progressBar.setVisibility(View.GONE);
                    });
                    builder.create();
                    builder.show();
                }
            } else {
                Toast.makeText(SliderIntroActivity.this, "FOI", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRequest() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Falha ao fazer login", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //openRegister();
                            validateRegisterByEmail(user.getEmail());
//                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SliderIntroActivity.this, "ERRO", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void addIndicators(int position) {

        linearLayout.removeAllViews();

        for (int i = 0; i < indCount; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_panorama_fish_eye_24));

//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24, 24);
            params.setMargins(8, 0, 8, 0);

            linearLayout.addView(indicators[i], params);
        }

        if (indicators.length > 0) {
            indicators[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lens_24));
        }

    }

    public void clickbtn(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressBar.setVisibility(View.VISIBLE);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (viewPager.getCurrentItem() == 4) {
                linearLayout.setVisibility(View.INVISIBLE);
                viewPager.setPagingEnabled(false);
            }

        }

        @Override
        public void onPageSelected(int position) {
            addIndicators(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void openRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    public void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    public void register(View view) {
        openRegister();
    }

    public void validateRegisterByEmail(String email) {
        ClientService clientService = retrofit.create(ClientService.class);
        Call<Client> call = clientService.validateRegisterByEmail(email);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                ClientDAO clientDAO = new ClientDAO(getApplicationContext());
                if (response.isSuccessful()) {
                    client = response.body();
                    if (client != null) {
                        if (client.getIdCli() > 0) {
                            if (clientDAO.save()) openMain();
                        } else
                            openRegister();
                    } else {
                        openRegister();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.i("ERRO", t.getMessage());
            }
        });

    }

}