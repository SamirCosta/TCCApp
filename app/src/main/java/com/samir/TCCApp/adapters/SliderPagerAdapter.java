package com.samir.TCCApp.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.textfield.TextInputLayout;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;

import java.util.Arrays;

import static com.samir.TCCApp.activities.SliderIntroActivity.progressBar;

public class SliderPagerAdapter extends PagerAdapter {
    private int[] layouts;
    private LayoutInflater layoutInflater;
    private Context context;
    public static View view;

    public SliderPagerAdapter(int[] layouts, Context context) {
        this.layouts = layouts;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        view = layoutInflater.inflate(layouts[position], container, false);

//        Log.i("AQ", "" + position);
        if (position > 2) {
            Button btn = view.findViewById(R.id.btnLoginSlider);
            TextInputLayout editUser, editPassword;
            editUser = view.findViewById(R.id.editEmailLoginSlider);
            editPassword = view.findViewById(R.id.editPasswordLogin);

            if (btn != null && editUser != null && editPassword != null) {
                btn.setOnClickListener(c -> {

                    if (editUser.getEditText().getText().toString().isEmpty()) {
                        editUser.setError("Campo obrigatório");
                    }
                    if (editPassword.getEditText().getText().toString().isEmpty()) {
                        editPassword.setError("Campo obrigatório");
                    }

                    if (!verify(editUser, editPassword)) {
                        progressBar.setVisibility(View.VISIBLE);
                        ClientDAO clientDAO = new ClientDAO(context);
                        clientDAO.validateLogin(
                                editUser.getEditText().getText().toString(),
                                editPassword.getEditText().getText().toString(),
                                view);
                    }

                });
            }
        }

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        View view = (View) object;
        container.removeView(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean verify(TextInputLayout... editTexts) {
        return Arrays.stream(editTexts).anyMatch(e -> e.getEditText().getText().toString().isEmpty());
    }


}
