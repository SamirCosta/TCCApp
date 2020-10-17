package com.samir.TCCApp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;

public class DadosFragment extends Fragment {
    private TextInputEditText editName, editEmail, editFamilyName;
    private TextView tvEnd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmailLogin);
        editFamilyName = view.findViewById(R.id.editFamilyName);
        tvEnd = view.findViewById(R.id.tvEndData);

        tvEnd.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (signInAccount != null) {
            editName.setText(signInAccount.getGivenName());
            editEmail.setText(signInAccount.getEmail());
            editFamilyName.setText(signInAccount.getFamilyName());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (AddressActivity.addressess != null) {
            tvEnd.setText(AddressActivity.addressess.getAddress());
        }
    }

}