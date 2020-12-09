package com.samir.TCCApp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;
import com.samir.TCCApp.utils.MaskEditUtil;

import java.util.Arrays;

import static com.samir.TCCApp.DAO.ClientDAO.client;

public class DadosFragment extends Fragment {
    private TextInputLayout editEmailUpdateLayout, editPasswUpdateLayout, editNameUpdateLayout;
    private TextInputEditText editName, editEmail, editCPF, editCel, editSenha;
    private TextView tvEnd;
    private EditText editComp;
    private Button btnUpdate;

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
        ref(view);
        updateCli();

        tvEnd.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

        editCPF.addTextChangedListener(MaskEditUtil.mask(editCPF, "###.###.###-##"));
        editCel.addTextChangedListener(MaskEditUtil.mask(editCel, "(##) #####-####"));

        if (client != null){
            String name = client.getNameCli();
            String[] array = name.split(" ");

            editName.setText(array[0]);
            editEmail.setText(client.getEmailCli());
            editCPF.setText(client.getCPF());
            editCel.setText("" + client.getCelCli());
            editSenha.setText(client.getUser().getPassword());
            editComp.setText(client.getComp());
        }

    }

    private void updateCli() {
        btnUpdate.setOnClickListener(v -> {
            if (editEmail.getText().toString().isEmpty())
                editEmailUpdateLayout.setError("Campo obrigatório");
            if (editSenha.getText().toString().isEmpty())
                editPasswUpdateLayout.setError("Campo obrigatório");
            if (editName.getText().toString().isEmpty())
                editNameUpdateLayout.setError("Campo obrigatório");

            if (!verify(editEmail.getText().toString(), editSenha.getText().toString(), editName.getText().toString())) {
                client.setAddressess(AddressActivity.addressess);
                client.setCPF(editCPF.getText().toString().replace(".", "").replace("-", ""));
                client.setNameCli(editName.getText().toString() /*+ " " + editFamilyName.getText().toString()*/);
                client.setEmailCli(editEmail.getText().toString());
                client.setCelCli(Long.parseLong(editCel.getText().toString().replace("(", "").replace(")", "")
                        .replace("-", "").replace(" ", "")));
                client.setComp(editComp.getText().toString());
                client.setNumEdif(1);

                //User user = new User();
                client.getUser().setPassword(editSenha.getText().toString());
                client.setUser(client.getUser());

                ClientDAO clientDAO = new ClientDAO(getActivity());
                clientDAO.updateClient(client, v, getActivity());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean verify(String... data) {
        return Arrays.stream(data).anyMatch(String::isEmpty);
//        return Arrays.stream(data).anyMatch(e -> e.getEditText().getText().toString().isEmpty());
    }

    private void ref(View view) {
        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmailLogin);
        tvEnd = view.findViewById(R.id.tvEndData);
        editCPF = view.findViewById(R.id.editCPF);
        editCel = view.findViewById(R.id.editCel);
        editSenha = view.findViewById(R.id.editPassw);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        editComp = view.findViewById(R.id.editComp);
        editPasswUpdateLayout = view.findViewById(R.id.editPasswUpdateLayout);
        editEmailUpdateLayout = view.findViewById(R.id.editEmailUpdateLayout);
        editNameUpdateLayout = view.findViewById(R.id.editNameUpdateLayout);
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