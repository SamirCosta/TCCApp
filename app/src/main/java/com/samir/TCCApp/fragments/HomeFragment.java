package com.samir.TCCApp.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.activities.AddressActivity;
import com.samir.TCCApp.activities.MainActivity;
import com.samir.TCCApp.activities.PaymentActivity;
import com.samir.TCCApp.adapters.BagAdapter;
import com.samir.TCCApp.classes.Product;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static BottomNavigationViewEx bottomNavigationViewEx;
    private MotionLayout motionLayout;
    private View view;
    private Button btn;
    private RecyclerView.LayoutManager layoutManager;
    public static TextView tvEmpty, end, tvTotalVal;
    public static ImageView emptyBag;
    public static ArrayList<Product> bagArrayListItem;
    public static RecyclerView recyclerViewBag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bagArrayListItem = new ArrayList<>();
        /*for(int i = 0; i < 11; i++){
            Product product = new Product("Name " + i, R.drawable.taco);
            arrayListItem.add(product);
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        if (AddressActivity.addressess != null) {
            if (AddressActivity.addressess.getAddress() != null)
                end.setText(AddressActivity.addressess.getAddress());
        }
        recyclerViewBag.getAdapter().notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ref(view);
        configRecycler();
        motionConfig();
        setName();
        bottomConfig();

        end.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
//            getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.slide_in_up);
        });

        btn.setOnClickListener(a -> {
            startActivity(new Intent(getActivity(), PaymentActivity.class));
        });

        if (bagArrayListItem.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            emptyBag.setVisibility(View.VISIBLE);
            tvTotalVal.setVisibility(View.GONE);
        }

        bottomNavigationViewEx.setCurrentItem(MainActivity.page);

        getActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (motionLayout.getVisibility() == View.VISIBLE)
                    motionLayout.transitionToStart();
                else
                    getActivity().finish();

            }
        });

        return view;
    }

    private void configRecycler() {

        recyclerViewBag = view.findViewById(R.id.recyclerBag);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewBag.setLayoutManager(layoutManager);
        recyclerViewBag.setHasFixedSize(true);
        BagAdapter bagAdapter = new BagAdapter(bagArrayListItem);
        recyclerViewBag.setAdapter(bagAdapter);

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bagArrayListItem.remove(viewHolder.getAdapterPosition());
//                recyclerViewBag.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                recyclerViewBag.getAdapter().notifyDataSetChanged();
                if (bagArrayListItem.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    emptyBag.setVisibility(View.VISIBLE);
                    tvTotalVal.setVisibility(View.GONE);
                }
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerViewBag);

        recyclerViewBag.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (Math.abs(velocityY) > 4000) {
                    velocityY = (int) (4000 * Math.signum((double) velocityY));
                    recyclerViewBag.fling(velocityX, velocityY);
                    return true;
                }

                if (Math.abs(velocityY) > 2000 && recyclerViewBag.computeVerticalScrollOffset() < 600) {
                    velocityY = (int) (2000 * Math.signum((double) velocityY));
                    recyclerViewBag.fling(velocityX, velocityY);
                    return true;
                }

//                velocityY /= 2;
//                Log.i("AQUI", ""+velocityY);
//                Log.i("AQUI", ""+recyclerView.getTop());
//                Log.i("AQUI", ""+recyclerView.fling(velocityX, velocityY));
//                Log.i("AQUI", ""+recyclerView.computeVerticalScrollOffset());

//                return recyclerView.fling(velocityX, velocityY);
                return false;
            }
        });

    }

    private void bottomConfig() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerBottomNav, new BottomHomeFragment()).commit();

        bottomNavigationViewEx = view.findViewById(R.id.bottomNav);
        bottomNavigationViewEx.enableAnimation(true);
        /*bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableShiftingMode(true);*/

        itemColor((byte) 0);

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction1 = getChildFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.ic_home:
                    fragmentTransaction1.replace(R.id.containerBottomNav, new BottomHomeFragment()).commit();
                    clearColor();
                    itemColor((byte) 0);
                    return true;
                case R.id.ic_cardapio:
//                        view.findViewById(R.id.layoutFilters).setVisibility(View.VISIBLE);
                    clearColor();
                    itemColor((byte) 1);
                    fragmentTransaction1.replace(R.id.containerBottomNav, new CardapioFragment()).commit();
                    return true;
                case R.id.ic_pedidos:
                    clearColor();
                    itemColor((byte) 2);
                    fragmentTransaction1.replace(R.id.containerBottomNav, new PedidosFragment()).commit();
                    return true;
            }

            return false;
        });
    }

    private void setName() {
        TextView tvName = view.findViewById(R.id.tvNameHome);

        /*GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        FirebaseUser userFace = FirebaseAuth.getInstance().getCurrentUser();
        if (signInAccount != null) {
            tvName.setText(signInAccount.getGivenName());
        } else if (userFace != null) {
            String name = userFace.getDisplayName();
            String[] array = name.split(" ");
            tvName.setText(array[0]);
        } else {*/
        if (ClientDAO.client != null)
            tvName.setText(ClientDAO.client.getNameCli());
        //}
    }

    private void motionConfig() {

        view.findViewById(R.id.bag).setOnClickListener(v -> {
            motionLayout.setVisibility(View.VISIBLE);
            if (motionLayout.getProgress() == 0) {
                motionLayout.transitionToEnd();
            } else {
                motionLayout.transitionToStart();
            }
        });

        motionLayout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (motionLayout.getProgress() == 0) {
                    motionLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    private void itemColor(Byte position) {
        bottomNavigationViewEx.setIconTintList(position, ColorStateList.valueOf(getResources().getColor(R.color.red_light)));

    }

    private void clearColor() {
        for (int i = 0; i <= bottomNavigationViewEx.getItemCount(); i++) {
            bottomNavigationViewEx.setIconTintList(i, ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        }
    }

    private void ref(View view) {
        end = view.findViewById(R.id.tvEndHome);
        btn = view.findViewById(R.id.btnPay);
        tvEmpty = view.findViewById(R.id.tvBagEmpty);
        tvTotalVal = view.findViewById(R.id.tvTotalNumBag);
        motionLayout = view.findViewById(R.id.motionHome);
        emptyBag = view.findViewById(R.id.imgBagEmpty);
    }

}