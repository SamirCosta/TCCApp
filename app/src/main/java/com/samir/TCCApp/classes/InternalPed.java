package com.samir.TCCApp.classes;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static com.samir.TCCApp.utils.Helper.ARQUIVO_PED;

public class InternalPed implements Serializable {

    private final ArrayList<PedidoView> pedidoViewsArray;

    public InternalPed(Context context) {
        this.pedidoViewsArray = getInternalPed(context);
    }

    public ArrayList<PedidoView> getPedidoViews() {
        return pedidoViewsArray;
    }

    public void setPedidoViews(PedidoView pedidoView, InternalPed internalPed, Context context) {
        this.pedidoViewsArray.add(pedidoView);
        savePed(internalPed, context);
    }

    public static ArrayList<PedidoView> getInternalPed(Context context) {
        ArrayList<PedidoView> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(context.getFileStreamPath(ARQUIVO_PED));
            ObjectInputStream ois = new ObjectInputStream(fis);
            InternalPed internalPed = (InternalPed) ois.readObject();
            list = internalPed.getPedidoViews();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void savePed(InternalPed internalPed, Context context) {
        try {
            FileOutputStream fos = new FileOutputStream(context.getFileStreamPath(ARQUIVO_PED));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(internalPed);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
