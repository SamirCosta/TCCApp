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
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;

import static com.samir.TCCApp.DAO.ClientDAO.client;

public class DadosFragment extends Fragment {
    private TextInputEditText editName, editEmail, editFamilyName, editCPF, editCel, editSenha;
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
        editCPF = view.findViewById(R.id.editCPF);
        editCel = view.findViewById(R.id.editCel);
        editSenha = view.findViewById(R.id.editPassw);

        tvEnd.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (signInAccount != null) {
            editName.setText(signInAccount.getGivenName());
            editEmail.setText(signInAccount.getEmail());
            editFamilyName.setText(signInAccount.getFamilyName());
        } else {
            String name = client.getNameCli();
            String[] array = name.split(" ");

            editName.setText(array[0]);
            editEmail.setText(client.getEmailCli());
            editCPF.setText(client.getCPF());
            editCel.setText("" + client.getCelCli());
            editSenha.setText(client.getUser().getPassword());

            StringBuilder nam = new StringBuilder();
            for (int i = 1; i < array.length; i++){
                nam.append(" " + array[i]);
            }
                editFamilyName.setText(nam.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (AddressActivity.addressess != null) {
            if (AddressActivity.addressess.getAddress() != null)
            tvEnd.setText(AddressActivity.addressess.getAddress());
        }
    }

}