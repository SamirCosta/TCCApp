package com.samir.TCCApp.classes;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static com.samir.TCCApp.utils.Helper.ARQUIVO_BAG;

public class InternalBag implements Serializable {

    ArrayList<Product> productArrayList;

    public InternalBag(Context context) {
        this.productArrayList = getInternalProducts(context);
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(Product product, InternalBag internalBag, Context context) {
        this.productArrayList.add(product);
        save(internalBag, context);
    }

    public void removeInternalTracks(int index, InternalBag internalBag, Context context) {
        this.productArrayList.remove(index);
        save(internalBag, context);
    }

    public static ArrayList<Product> getInternalProducts(Context context) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(context.getFileStreamPath(ARQUIVO_BAG));
            ObjectInputStream ois = new ObjectInputStream(fis);
            InternalBag internalBag = (InternalBag) ois.readObject();
            list = internalBag.getProductArrayList();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save(InternalBag internalBag, Context context) {
        try {
            FileOutputStream fos = new FileOutputStream(context.getFileStreamPath(ARQUIVO_BAG));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(internalBag);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
