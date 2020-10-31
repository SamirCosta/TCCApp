package com.samir.TCCApp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.PaymentActivity;
import com.samir.TCCApp.activities.ScannerActivity;
import com.samir.TCCApp.adapters.SliderAdapterExample;
import com.samir.TCCApp.adapters.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BottomHomeFragment extends Fragment {
    private SliderView sliderView;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    private AlertDialog dialog;
    private Spinner spinnerType;
    private CardView cardView;
    private NumberPicker numberPicker;
    private TextView textView;
    private EditText date, time;
    private DatePickerDialog.OnDateSetListener setListener;
    private int hour, min;
    private String selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SliderItem sliderItem = new SliderItem(R.drawable.nachos);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.guacamole);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.quesadilla);
        mSliderItems.add(sliderItem);

        sliderItem = new SliderItem(R.drawable.taco);
        mSliderItems.add(sliderItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cardDelivery).setOnClickListener(v -> HomeFragment.bottomNavigationViewEx.setCurrentItem(1));

        view.findViewById(R.id.cardPedRest).setOnClickListener(c -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("Peça no restaurante");
            builder.setMessage("Este pedido é baseado nos itens da sacola, deseja continuar?");
            builder.setPositiveButton("Sim", (dialog, which) -> getActivity().startActivity(new Intent(getActivity(), ScannerActivity.class)))
                    .setNegativeButton("Ir para o cardápio", (dialog, which) -> HomeFragment.bottomNavigationViewEx.setCurrentItem(1));
            builder.create();
            builder.show();
        });

        view.setOnClickListener(a -> {
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setTitle("Agende seu pedido");
            View mView = getLayoutInflater().inflate(R.layout.sched_layout, null);
            layoutConfig(mView);
            mBuilder.setPositiveButton("OK", (dialog, which) -> {

                if (!selected.equals("Localização da mesa") && !textView.getText().toString().equals("Quantidade de lugares")
                        && date.getText().toString() != null && time.getText().toString() != null) {

                    getActivity().startActivity(new Intent(getActivity(), PaymentActivity.class));
                }else {
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

            }).setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss());
           /* recyclerView = mView.findViewById(R.id.recyclerOrig);
            Methods.configRecycler(recyclerView, MainActivity.equipamentos, getActivity(), (byte) 1);
            itemClick();*/
            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.show();
        });

        sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapterExample(getActivity(), mSliderItems));

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(getResources().getColor(R.color.transparent_white));
        sliderView.setScrollTimeInSec(3);
        sliderView.setSliderAnimationDuration(1500);
        sliderView.startAutoCycle();

    }

    private void layoutConfig(View mView) {
        spinnerType = mView.findViewById(R.id.spinnerSchedType);
        cardView = mView.findViewById(R.id.cardViewSched);
        textView = mView.findViewById(R.id.tvQtdPlace);
        date = mView.findViewById(R.id.editDate);
        time = mView.findViewById(R.id.editTime);

        String[] types = {"Localização da mesa", "Arejado", "Coberto"};
        ArrayList<String> arrayListType = new ArrayList<>(Arrays.asList(types));
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<>(getActivity(), R.layout.tv_spinner, arrayListType);
        arrayAdapterType.setDropDownViewResource(R.layout.tv_spinner);
        spinnerType.setAdapter(arrayAdapterType);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        setListener = (view, year, month, dayOfMonth) -> {
            month++;
            String mDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + year;
            date.setText(mDate);
        };

        date.setOnClickListener(s -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view, year, month, dayOfMonth) -> {
                month++;
                String mDate = dayOfMonth + "/" + month + "/" + year;
                date.setText(mDate);
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        time.setOnClickListener(n -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    (view, hourOfDay, minute) -> {
                        hour = hourOfDay;
                        min = minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(0, 0, 0, hour, min);
                        time.setText(DateFormat.format("hh:mm aa", calendar1));
                    }, 12, 0, true
            );
            timePickerDialog.updateTime(hour, min);
            timePickerDialog.show();
        });

        cardView.setOnClickListener(d -> {
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setTitle("Defina a quantidade de lugares");
            View view = getLayoutInflater().inflate(R.layout.num_places, null);

            numberPicker = view.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(7);

            mBuilder.setPositiveButton("OK", (dialog, which) -> {
                textView.setText(String.valueOf(numberPicker.getValue()));
                dialog.dismiss();
            });

            mBuilder.setView(view);
            dialog = mBuilder.create();
            dialog.show();
        });

    }

}