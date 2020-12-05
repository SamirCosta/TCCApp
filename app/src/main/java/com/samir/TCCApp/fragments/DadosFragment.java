package com.samir.TCCApp.fragments;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;
import com.samir.TCCApp.utils.MaskEditUtil;

import static com.samir.TCCApp.DAO.ClientDAO.client;

public class DadosFragment extends Fragment {
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


        tvEnd.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

        btnUpdate.setOnClickListener(v -> {
            client.setAddressess(AddressActivity.addressess);
            client.setCPF(editCPF.getText().toString());
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
        });

        /*GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (signInAccount != null) {
            editName.setText(signInAccount.getGivenName());
            editEmail.setText(signInAccount.getEmail());
            editFamilyName.setText(signInAccount.getFamilyName());
        } else*/ if (client != null){
            String name = client.getNameCli();
            String[] array = name.split(" ");

//            Log.i("SOBRENOME", client.getNameCli());

            editName.setText(array[0]);
            editEmail.setText(client.getEmailCli());
            editCPF.setText(client.getCPF());
            editCel.setText("" + client.getCelCli());
            editSenha.setText(client.getUser().getPassword());
        }
        editCPF.addTextChangedListener(MaskEditUtil.mask(editCPF, "###.###.###-##"));
        editCel.addTextChangedListener(MaskEditUtil.mask(editCel, "(##) #####-####"));
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