package com.samir.TCCApp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.samir.TCCApp.classes.Places;

import java.util.ArrayList;

public class PlaceAutoSuggest extends ArrayAdapter implements Filterable {

    ArrayList<String> result;
    Context context;
    int resource;
    Places places = new Places();

    public PlaceAutoSuggest(Context context, int resId){
        super(context, resId);
        this.context = context;
        this.resource = resId;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null){
                    result = places.autoComplete(constraint.toString());
                    filterResults.values = result;
                    filterResults.count = result.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /*public PlaceAutoSuggest(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }*/
}
