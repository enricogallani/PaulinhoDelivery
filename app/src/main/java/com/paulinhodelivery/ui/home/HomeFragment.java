package com.paulinhodelivery.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paulinhodelivery.R;
import com.paulinhodelivery.data.Restaurant;
import com.paulinhodelivery.databinding.FragmentHomeBinding;
import com.paulinhodelivery.ui.RestaurantActivity;
import com.paulinhodelivery.ui.adapter.OnItemClickListener;
import com.paulinhodelivery.ui.adapter.RestaurantAdapter;
import com.paulinhodelivery.ui.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_home));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.list;

        homeViewModel.getList().observe(getViewLifecycleOwner(), this::showRestaurants);

        return root;
    }

    public void showRestaurants(List<Restaurant> restaurants) {
        RestaurantAdapter adapter = new RestaurantAdapter(restaurants);
        adapter.setOnItemClickListener((view, position) -> {
            Restaurant restaurant = restaurants.get(position);
            Intent intent = new Intent(getActivity(), RestaurantActivity.class);
            intent.putExtra("restaurant", restaurant);

            startActivity(intent);
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}