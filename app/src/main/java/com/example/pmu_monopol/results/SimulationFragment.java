package com.example.pmu_monopol.results;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmu_monopol.MainActivity;
import com.example.pmu_monopol.gameplay.DataViewModel;
import com.example.pmu_monopol.gameplay.GamePlayFragmentArgs;
import com.example.pmu_monopol.gameplay.GameStepViewModle;


public class SimulationFragment extends Fragment {


    private DataViewModel dataViewModel;
    private GameStepViewModle stepViewModle;
    private MainActivity mainActivity;
    private int currentId;
    private NavController navController;
    private SimulationSurface sim;

    public SimulationFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity=(MainActivity)requireActivity();
        ViewModelProvider modelProvider=new ViewModelProvider(mainActivity);
        dataViewModel=modelProvider.get(DataViewModel.class);
        stepViewModle=modelProvider.get(GameStepViewModle.class);
        currentId= SimulationFragmentArgs.fromBundle(requireArguments()).getIdGame();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sim= new SimulationSurface(getContext(),dataViewModel,stepViewModle,currentId);
        return sim;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        sim.navController=navController;


    }
}